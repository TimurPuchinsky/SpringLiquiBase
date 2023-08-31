package com.example.springliquidbase.domainservice;

import com.example.springliquidbase.domain.common.ExportModel;
import com.example.springliquidbase.domain.common.PageResultModel;
import com.example.springliquidbase.domain.translate.TranslatePageModel;
import com.example.springliquidbase.domain.translate.TranslateResultModel;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
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

    //count++, пагинация для поиска словарей. в цикле добавляются все значения модели. результат записывать в список/мапу.
    //какое то обогащение (использование сервисов для составление модели)
    //собирать id в список и вызывать одним запросом. email юзера. инфо языка (с какого на какой)

    public MultipartFile export() throws IOException {

        var translatePageModel = new TranslatePageModel();
        translatePageModel.setPageNum(0);
        translatePageModel.setPageSize(100);
        PageResultModel<TranslateResultModel> page;

        List<ExportModel> exportmodelList = new ArrayList<>();
        List<UUID> authorIds = new ArrayList<>();
        List<UUID> changerIds = new ArrayList<>();
        List<String> wordsFrom = new ArrayList<>();
        List<String> wordsTo = new ArrayList<>();
        List<LocalDateTime> created = new ArrayList<>();
        List<LocalDateTime> changed = new ArrayList<>();
        List<UUID> dictionariesIds = new ArrayList<>();

        do {
            page = translateService.getPage(translatePageModel);
            translatePageModel.setPageNum(translatePageModel.getPageNum() + 1);
            for (var model : page.getList()) {
                authorIds.add(model.getAuthor_id());
                changerIds.add(model.getChanger_id());
                wordsFrom.add(model.getWordStringFrom());
                wordsTo.add(model.getWordStringTo());
                created.add(model.getCreated());
                changed.add(model.getChanged());
                dictionariesIds.add(model.getDictionary_id());
            }
        } while (page.getList().size() == translatePageModel.getPageSize());

        Set<UUID> dictionariesIdsSet = new HashSet<>(dictionariesIds);
        List<UUID> uniqueDictionaries = new ArrayList<>(dictionariesIdsSet);

        var authorsMap = userService.getUsersListByIds(authorIds);
        var changersMap = userService.getUsersListByIds(changerIds);
        var dictionariesMap = dictionaryService.getDictionariesById(dictionariesIds);
        var languagesMap = languageService.getLanguagesByDictionaryIds(dictionariesIds);

        for (int i = 0; i < authorIds.size(); i++) {
            var model = new ExportModel();
            model.setWord_from(wordsFrom.get(i));
            model.setWord_to(wordsTo.get(i));
            model.setAuthor_fio(authorsMap.get(authorIds.get(i)).getSurname() + " " +
                    authorsMap.get(authorIds.get(i)).getName() + " " +
                    authorsMap.get(authorIds.get(i)).getFather());
            model.setCreated(created.get(i));
            model.setChanger_fio(changersMap.get(changerIds.get(i)).getSurname() + " " +
                    changersMap.get(changerIds.get(i)).getName() + " " +
                    changersMap.get(changerIds.get(i)).getFather());
            model.setChanged(changed.get(i));
            model.setAuthor_id(authorIds.get(i));
            model.setChanger_id(changerIds.get(i));
            model.setDictionary_id(dictionariesIds.get(i));
            model.setAuthor_email(authorsMap.get(authorIds.get(i)).getEmail());
            exportmodelList.add(model);
        }

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