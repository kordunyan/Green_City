package com.trash.green.city.service;

import com.trash.green.city.constants.ConvertationRate;
import com.trash.green.city.domain.*;
import com.trash.green.city.repository.TrashExportationRepository;
import com.trash.green.city.service.dto.TrashExportationDTO;
import com.trash.green.city.service.dto.TrashExportationWithImagesDTO;
import com.trash.green.city.service.mapper.TrashExportationMapper;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CSVService {

    private final Logger log = LoggerFactory.getLogger(TrashExportationService.class);

    private final TrashExportationRepository trashExportationRepository;

    private final TrashExportationMapper trashExportationMapper;

    public CSVService(TrashExportationRepository trashExportationRepository, TrashExportationMapper trashExportationMapper) {
        this.trashExportationRepository = trashExportationRepository;
        this.trashExportationMapper = trashExportationMapper;
    }

    @Transactional(readOnly = true)
    public List<TrashExportationWithImagesDTO> findAllByOsbb(String lessThan, String greaterThan, Long id) {
        log.debug("Request to get findAllByOsbb");
        List<TrashExportation> allByOsbbId = trashExportationRepository.findAllByOsbbIdOrderByDateDesc(id);
        List<TrashExportationWithImagesDTO> result = new ArrayList<>();
        for (TrashExportation trashExportation : allByOsbbId) {
            TrashExportationDTO exportationDTO = trashExportationMapper.toDto(trashExportation);
            TrashExportationWithImagesDTO dto = new TrashExportationWithImagesDTO();
            dto.setOsbb(exportationDTO.getOsbb());
            dto.setTrash_type(exportationDTO.getTrash_type());
            dto.setDate(exportationDTO.getDate());
            dto.setIs_wash(exportationDTO.getIs_wash());
            dto.setWeight(exportationDTO.getWeight());
            dto.setId(exportationDTO.getId());

            List<String> emptyTrash = trashExportation
                .getEmptyTrashImages()
                .stream()
                .map(EmptyTrashImages::getPath)
                .collect(Collectors.toList());

            List<String> fullTrash = trashExportation
                .getFullTrashImages()
                .stream()
                .map(FullTrashImages::getPath)
                .collect(Collectors.toList());

            dto.setEmptyTrashImages(emptyTrash);
            dto.setFullTrashImages(fullTrash);
            result.add(dto);
        }
        return result;
    }

    @Transactional(readOnly = true)
    public List<TrashExportationReportWithType> findAllGroupedByOsbbId() {
        log.debug("Request to findAllBroupedByOsbbId ");
        List<Object[]> a = trashExportationRepository.findAllGr();
        List<TrashExportationReport> aa = new ArrayList<>();
        for (Object[] obj : a) {
            Integer weigh = ((BigInteger) obj[0]).intValue();
            String name = (String) obj[1];
            String address = (String) obj[2];
            String type = (String) obj[3];
            aa.add(new TrashExportationReport(weigh, address, name, type));
        }

        List<TrashExportationReportWithType> aaa = new ArrayList<>();

        aa.forEach(item -> {
            TrashExportationReportWithType reportItem = aaa
                .stream()
                .filter(i -> i.getName().equals(item.getName()))
                .findAny()
                .orElse(new TrashExportationReportWithType(item.getAddress(), item.getName()));

            ConvertationRate convertationRate = ConvertationRate.getByType(item.getTrash_type());

            if (ConvertationRate.ORGANIC.equals(convertationRate)) reportItem.setOrganic(item.getWeight());

            if (ConvertationRate.PLASTIC.equals(convertationRate)) reportItem.setPlastic(item.getWeight());

            if (ConvertationRate.MIXED.equals(convertationRate)) reportItem.setMixed(item.getWeight());

            if (ConvertationRate.PAPER.equals(convertationRate)) reportItem.setPaper(item.getWeight());
        });
        return aaa;
    }
}
