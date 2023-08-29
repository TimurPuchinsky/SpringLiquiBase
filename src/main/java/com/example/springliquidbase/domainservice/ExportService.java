package com.example.springliquidbase.domainservice;

import com.example.springliquidbase.domain.common.ExportModel;
import com.example.springliquidbase.domain.common.PageResultModel;
import com.example.springliquidbase.domain.dictionary.DictionaryPageModel;
import com.example.springliquidbase.domain.translate.TranslatePageModel;
import com.example.springliquidbase.domain.translate.TranslateResultModel;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ExportService {

    public ExportService(DictionaryService dictionaryService, TranslateService translateService) {
        this.dictionaryService = dictionaryService;
        this.translateService = translateService;
    }

    private final DictionaryService dictionaryService;
    private final TranslateService translateService;
    TranslatePageModel translatePageModel;
    ExportModel exportModel;

    //count++, пагинация для поиска словарей. в цикле добавляются все значения модели. результат записывать в список/мапу.

    public MultipartFile export() {
        translatePageModel = new TranslatePageModel();
        translatePageModel.setPageNum(0);
        translatePageModel.setPageSize(100);
        PageResultModel<TranslateResultModel> page;

        do {
            page = translateService.getPage(translatePageModel);
            translatePageModel.setPageNum(translatePageModel.getPageNum() + 1);
            //какое то обогощение (использование сервисов для составление модели)
            //exportModel.setWord_from();
        } while (page.getList().size() == translatePageModel.getPageSize());

        return null;
    }
}
