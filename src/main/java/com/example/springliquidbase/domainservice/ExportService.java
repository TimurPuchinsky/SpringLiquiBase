package com.example.springliquidbase.domainservice;

import com.example.springliquidbase.domain.common.ExportModel;
import com.example.springliquidbase.domain.common.PageResultModel;
import com.example.springliquidbase.domain.dictionary.DictionaryModel;
import com.example.springliquidbase.domain.language.LanguageModel;
import com.example.springliquidbase.domain.translate.TranslatePageModel;
import com.example.springliquidbase.domain.translate.TranslateResultModel;
import com.example.springliquidbase.domain.user.UserModel;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ExportService {

    public ExportService(TranslateService translateService, UserService userService,
                         DictionaryService dictionaryService, LanguageService languageService) {
        this.translateService = translateService;
        this.userService = userService;
        this.dictionaryService = dictionaryService;
        this.languageService = languageService;
    }

    private final LanguageService languageService;
    private final DictionaryService dictionaryService;
    private final TranslateService translateService;
    private final UserService userService;


    //перевод сервисов, обогащение модели и запись в excel в do/while
    public MultipartFile export() throws IOException {

        var translatePageModel = new TranslatePageModel();
        translatePageModel.setPageNum(0);
        translatePageModel.setPageSize(100);
        PageResultModel<TranslateResultModel> page;

        List<ExportModel> exportmodelList = new ArrayList<>();
        List<UUID> authorIds = new ArrayList<>();
        List<UUID> changerIds = new ArrayList<>();
        List<TranslateResultModel> pageList = new ArrayList<>();
        List<UUID> dictionariesIds = new ArrayList<>();

        Map<UUID, UserModel> authorsMap;
        Map<UUID, UserModel> changersMap;
        Map<UUID, DictionaryModel> dictionariesMap;
        Map<UUID, List<LanguageModel>> languagesMap;

        do {
            page = translateService.getPage(translatePageModel);
            translatePageModel.setPageNum(translatePageModel.getPageNum() + 1);
            authorIds.addAll(page.getList().stream().map(TranslateResultModel::getAuthor_id).toList());
            changerIds.addAll(page.getList().stream().map(TranslateResultModel::getChanger_id).toList());
            dictionariesIds.addAll(page.getList().stream().map(TranslateResultModel::getDictionary_id).toList());
            pageList.addAll(page.getList());

            authorsMap = userService.getUsersListByIds(authorIds);
            changersMap = userService.getUsersListByIds(changerIds);
            dictionariesMap = dictionaryService.getDictionariesById(dictionariesIds);
            languagesMap = languageService.getLanguagesByDictionaryIds(dictionariesMap);

            for (var pageModel : pageList) {
                var model = new ExportModel();

                model.setWord_from(pageModel.getWordStringFrom());
                model.setWord_to(pageModel.getWordStringTo());

                model.setAuthor_fio(
                        authorsMap.get(pageModel.getAuthor_id()).getSurname() + " " +
                                authorsMap.get(pageModel.getAuthor_id()).getName() + " " +
                                authorsMap.get(pageModel.getAuthor_id()).getFather());

                model.setCreated(pageModel.getCreated());

                model.setChanger_fio(
                        changersMap.get(pageModel.getChanger_id()).getSurname() + " " +
                                changersMap.get(pageModel.getChanger_id()).getName() + " " +
                                changersMap.get(pageModel.getChanger_id()).getFather());

                model.setChanged(pageModel.getChanged());
                model.setAuthor_id(pageModel.getAuthor_id());
                model.setChanger_id(pageModel.getChanger_id());
                model.setDictionary_id(pageModel.getDictionary_id());
                model.setAuthor_email(authorsMap.get(pageModel.getAuthor_id()).getEmail());
                exportmodelList.add(model);
            }
        } while (page.getList().size() == translatePageModel.getPageSize());

        Set<UUID> dictionariesIdsSet = new HashSet<>(dictionariesIds);
        List<UUID> uniqueDictionaries = new ArrayList<>(dictionariesIdsSet);

        var workbook = new XSSFWorkbook();

        for (int i = 0; i < dictionariesMap.size(); i++) {
            var model = dictionariesMap.get(uniqueDictionaries.get(i));
            var sheet = workbook.createSheet(model.getName());
            var headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue(languagesMap.get(model.getId()).get(0).getName());
            headerRow.createCell(1).setCellValue(languagesMap.get(model.getId()).get(1).getName());
            headerRow.createCell(2).setCellValue("Добавил");
            headerRow.createCell(3).setCellValue("Дата добавления");
            headerRow.createCell(4).setCellValue("Изменил");
            headerRow.createCell(5).setCellValue("Дата изменения");
            headerRow.createCell(6).setCellValue("Почта создателя");

            int rowIndex = 1;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            for (var exportModel : exportmodelList) {
                if (exportModel.getDictionary_id().equals(model.getId())) {
                    var dataRow = sheet.createRow(rowIndex);
                    dataRow.createCell(0).setCellValue(exportModel.getWord_from());
                    dataRow.createCell(1).setCellValue(exportModel.getWord_to());
                    dataRow.createCell(2).setCellValue(exportModel.getAuthor_fio());
                    dataRow.createCell(3).setCellValue(exportModel.getCreated().format(formatter));
                    dataRow.createCell(4).setCellValue(exportModel.getChanger_fio());
                    dataRow.createCell(5).setCellValue(exportModel.getChanged().format(formatter));
                    dataRow.createCell(6).setCellValue(exportModel.getAuthor_email());
                    rowIndex++;
                }
            }
        }

        var outputFile = new File("export.xlsx");
        var outputStream = new FileOutputStream(outputFile);
        workbook.write(outputStream);

        outputStream.close();
        workbook.close();

        return null;
    }
}