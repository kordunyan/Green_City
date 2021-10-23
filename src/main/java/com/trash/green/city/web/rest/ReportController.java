package com.trash.green.city.web.rest;

import com.google.common.net.HttpHeaders;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.trash.green.city.domain.TrashExportationReport;
import com.trash.green.city.domain.TrashExportationReportWithType;
import com.trash.green.city.service.CSVService;
import com.trash.green.city.service.dto.TrashExportationDTO;
import com.trash.green.city.service.dto.TrashExportationWithImagesDTO;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportController {

    @Autowired
    private CSVService csvService;

    @GetMapping("/export-by-osbb-csv/")
    public void exportCSV(HttpServletResponse response) throws Exception {
        String filename = "report.csv";
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");

        // create a csv writer
        StatefulBeanToCsv<TrashExportationReportWithType> writer = new StatefulBeanToCsvBuilder<TrashExportationReportWithType>(
            response.getWriter()
        )
            .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
            .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
            .withOrderedResults(false)
            .build();

        writer.write(csvService.findAllGroupedByOsbbId());
    }

    @GetMapping("/export-by-all-osbb/")
    public void exportCSV(HttpServletResponse response, @RequestParam String lessThan, @RequestParam String greaterThan) throws Exception {
        String filename = "report.csv";
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");

        // create a csv writer
        StatefulBeanToCsv<TrashExportationDTO> writer = new StatefulBeanToCsvBuilder<TrashExportationDTO>(response.getWriter())
            .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
            .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
            .withOrderedResults(false)
            .build();
        //   writer.write(csvService.findAllGroupedByOsbbId(lessThan, greaterThan));
    }

    @GetMapping("/export-by-osbb/{id}")
    public List<TrashExportationWithImagesDTO> get(@PathVariable Long id, @RequestParam String lessThan, @RequestParam String greaterThan) {
        return csvService.findAllByOsbb(lessThan, greaterThan, id);
    }

    @GetMapping("/export-by-osbb/")
    public List<TrashExportationReportWithType> exportCSV() throws Exception {
        return csvService.findAllGroupedByOsbbId();
    }
}
