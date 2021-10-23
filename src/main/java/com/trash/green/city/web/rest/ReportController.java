package com.trash.green.city.web.rest;

import com.google.common.net.HttpHeaders;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.trash.green.city.service.CSVService;
import com.trash.green.city.service.dto.TrashExportationDTO;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportController {

    private CSVService csvService;

    @GetMapping("/export-by-osbb-csv/{id}")
    public void exportCSV(
        HttpServletResponse response,
        @PathVariable Integer id,
        @RequestParam String lessThan,
        @RequestParam String greaterThan
    ) throws Exception {
        String filename = "report.csv";
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");

        // create a csv writer
        StatefulBeanToCsv<TrashExportationDTO> writer = new StatefulBeanToCsvBuilder<TrashExportationDTO>(response.getWriter())
            .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
            .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
            .withOrderedResults(false)
            .build();

        writer.write(csvService.findAllByOsbb(lessThan, greaterThan, id));
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

        writer.write(csvService.findAllBroupedByOsbbId(lessThan, greaterThan));
    }

    @GetMapping("/export-by-osbb/{id}")
    public List<TrashExportationDTO> get(@PathVariable Integer id, @RequestParam String lessThan, @RequestParam String greaterThan) {
        return csvService.findAllBroupedByOsbbId(lessThan, greaterThan);
    }

    @GetMapping("/export-by-osbb/")
    public List<TrashExportationDTO> exportCSV(@RequestParam String lessThan, @RequestParam String greaterThan) throws Exception {
        return csvService.findAllBroupedByOsbbId(lessThan, greaterThan);
    }
}
