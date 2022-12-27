package com.example.test_spring_one.services;

import com.example.test_spring_one.Dto.DtoAmountProduct;
import com.example.test_spring_one.Dto.DtoCrude;
import com.example.test_spring_one.Dto.DtoProduct;
import com.example.test_spring_one.Dto.DtoUnit;
import com.example.test_spring_one.model.*;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CountService {

    private PCRepository pcRepository;
    private CrudeRepository crudeRepository;
    private ProductRepository productRepository;
    private UnitRepository unitRepository;

    public List<DtoUnit> getUnits() {
        return unitRepository.findAll()
                .stream()
                .map(unit -> {
                    DtoUnit dtoUnit = new DtoUnit();
                    dtoUnit.setName(unit.getName());
                    return dtoUnit;
                }).collect(Collectors.toList());
    }

    public String addUnit(String nameUnit){
        for (Unit unit : unitRepository.findAll()) {
            if(unit.getName().equals(nameUnit)){
                return null;
            }
        }
        Unit unit = new Unit();
        unit.setName(nameUnit);
        unitRepository.save(unit);
        return "\""+nameUnit+"\" успешно записан!";
    }

    public String addCrude(String nameCrude, String nameUnit) {
        for (Crude crude : crudeRepository.findAll()) {
            if(crude.getName().equals(nameCrude)){
                return "\""+nameCrude+"\" уже есть в базе";
            }
        }
        Crude crude = new Crude();
        crude.setName(nameCrude);
        for (Unit unit : unitRepository.findByName(nameUnit)) {
            crude.setUnit(unit);
            crude.setUnitId(unit.getId());
        }
        crudeRepository.save(crude);
        return "запись \"" + nameCrude + "\" успешно добавлена!";
    }

    public String deleteCrude(String nameCrude){
        for (Crude crude : crudeRepository.findAll()) {
            if(crude.getName().equals(nameCrude)){
                crudeRepository.delete(crude);
                return "\""+nameCrude+"\" удалено из базы";
            }
        }
        return null;
    }

    public List<DtoProduct> getProduct() {
        return productRepository.findAll()
                .stream()
                .map(product -> {
                    DtoProduct dtoProduct = new DtoProduct();
                    dtoProduct.setName(product.getName());
                    return dtoProduct;
                }).collect(Collectors.toList());
    }

    public List<DtoCrude> getCrudes() {
        return crudeRepository.findAll()
                .stream()
                .map(crude -> {
                    DtoCrude dtoCrude = new DtoCrude();
                    dtoCrude.setName(crude.getName());
                    return dtoCrude;
                }).collect(Collectors.toList());
    }

    public String addNewProduct(String name) {
        Product product = new Product();
        product.setName(name);
        productRepository.findAll().forEach(productSearch -> {
            if (productSearch.getName().equals(name)) {
                product.setId(productSearch.getId());
            }
        });
        Optional<Product> optionalProduct = productRepository.findById(product.getId());
        if (optionalProduct.isPresent()) {
            return "продукт есть в базе!";
        } else {
            productRepository.save(product);
            return "добавлен новый продукт!";
        }
    }

    public String addConsolidate(String productName, String crudeName, int count) {

        PrimaryKeyPC primaryKeyPC = new PrimaryKeyPC();
        ProductCrude productCrude = new ProductCrude();

        Product product = productRepository.findByName(productName);
        productCrude.setProduct(product);
        primaryKeyPC.setProductId(product.getId());

        Crude crude = crudeRepository.findByName(crudeName);
        productCrude.setCrude(crude);
        primaryKeyPC.setCrudeId(crude.getId());

        productCrude.setProductCrude(primaryKeyPC);
        productCrude.setCount(count);
        pcRepository.save(productCrude);

        return "добавлена новая сводная!";
    }

    public String counting(DtoAmountProduct[] dtoAP) {
        HashMap<String, Integer> countHashMap = new HashMap<>();
        StringBuilder builder = new StringBuilder();
        for (DtoAmountProduct dtoAmountProduct : dtoAP) {
            Product product = productRepository.findByName(dtoAmountProduct.getNameProduct());
            for (ProductCrude productCrude : pcRepository.findAll()) {
                if (productCrude.getProduct().equals(product)) {
                    String crudeName = productCrude.getCrude().getName();
                    Integer countProduct = productCrude.getCount() * dtoAmountProduct.getCountProduct();
                    countHashMap.merge(crudeName, countProduct, (oldValue, newValue) -> oldValue + newValue);
                }
            }
        }
        for (Map.Entry<String, Integer> entry : countHashMap.entrySet()) {
            String unit = crudeRepository.findByName(entry.getKey()).getUnit().getName();
            builder.append(entry.getKey() + " " + entry.getValue() + " " + unit + getFormatUnit(entry.getValue()) + "\n");
        }

        return builder.toString();
    }

    private String getFormatUnit(Integer countValue) {
        String stringValue = String.valueOf(countValue);
        String[] array = stringValue.split("");
        Pattern pattern = Pattern.compile("[2-4]");
        Matcher matcher = pattern.matcher(array[array.length - 1]);
        String lastDoubleValue = StringUtils.right(stringValue, 2);
        Pattern patternLast = Pattern.compile("[2-9][5-9]|1[0-9]|[0-9]0");
        Matcher matcherLast = patternLast.matcher(lastDoubleValue);
        if (matcherLast.matches()) {
            return "ов";
        }
        if (matcher.matches()) {
            return "а";
        }
        return "";
    }
}
