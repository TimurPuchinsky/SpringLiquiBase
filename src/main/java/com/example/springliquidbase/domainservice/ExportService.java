package com.example.springliquidbase.domainservice;

import com.example.springliquidbase.domain.common.ExportModel;
import com.example.springliquidbase.domain.common.PageResultModel;
import com.example.springliquidbase.domain.translate.TranslatePageModel;
import com.example.springliquidbase.domain.translate.TranslateResultModel;
import com.opencsv.CSVWriter;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ExportService {

    public ExportService(TranslateService translateService, UserService userService, DictionaryService dictionaryService, LanguageService languageService) {
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

        var authors = userService.getUsersFullnamesListByIds(authorIds);
        var changers = userService.getUsersFullnamesListByIds(changerIds);
        var dictionaries = dictionaryService.getDictionariesById(dictionariesIds);
        var languages = languageService.getLanguagesByDictionaryIds(dictionaries);

        for (int i = 0; i < authorIds.size() - 1; i++) {
            var model = new ExportModel();
            model.setWord_from(wordsFrom.get(i));
            model.setWord_to(wordsTo.get(i));
            model.setAuthor_fio(authors.get(i));
            model.setCreated(created.get(i));
            model.setChanger_fio(changers.get(i));
            model.setChanged(changed.get(i));
            exportmodelList.add(model);
        }

        var workbook = new XSSFWorkbook();
        var sheet = workbook.createSheet("ExportData");

        var headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Язык 1");
        headerRow.createCell(1).setCellValue("Язык 2");
        headerRow.createCell(2).setCellValue("Добавил");
        headerRow.createCell(3).setCellValue("Дата добавления");
        headerRow.createCell(4).setCellValue("Изменил");
        headerRow.createCell(5).setCellValue("Дата изменения");

        int rowIndex = 1;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        for (var model : exportmodelList) {
            var dataRow = sheet.createRow(rowIndex);
            dataRow.createCell(0).setCellValue(model.getWord_from());
            dataRow.createCell(1).setCellValue(model.getWord_to());
            dataRow.createCell(2).setCellValue(model.getAuthor_fio().toString());
            dataRow.createCell(3).setCellValue(model.getCreated().format(formatter));
            dataRow.createCell(4).setCellValue(model.getChanger_fio().toString());
            dataRow.createCell(5).setCellValue(model.getChanged().format(formatter));
            rowIndex++;
        }

        var outputFile = new File("export.xlsx");
        var outputStream = new FileOutputStream(outputFile);
        workbook.write(outputStream);

        outputStream.close();
        workbook.close();

        return null;
    }
}