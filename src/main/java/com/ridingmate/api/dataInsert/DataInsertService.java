package com.ridingmate.api.dataInsert;

import com.ridingmate.api.entity.BikeCompanyEntity;
import com.ridingmate.api.entity.BikeModelEntity;
import com.ridingmate.api.entity.BikeSpecEntity;
import com.ridingmate.api.entity.BikeYearEntity;
import com.ridingmate.api.repository.bikeCompany.BikeCompanyRepository;
import com.ridingmate.api.repository.bikeModel.BikeModelRepository;
import com.ridingmate.api.repository.BikeSpecRepository;
import com.ridingmate.api.repository.bikeYear.BikeYearRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

@Service
public class DataInsertService {

    @Autowired
    private BikeSpecRepository bikeSpecRepository;

    @Autowired
    private BikeCompanyRepository bikeCompanyRepository;

    @Autowired
    private BikeModelRepository bikeModelRepository;

    @Autowired
    private BikeYearRepository bikeYearRepository;

//    @Transactional
    public void jsonParse(){

        JSONParser parser = new JSONParser();

        Reader reader;

        {
            try {
                reader = new FileReader("bike_db.json");

                Object obj = parser.parse(reader);
                JSONArray jsonArray = (JSONArray) obj;

                if(jsonArray.size() > 0){
                    for(int i=0; i< jsonArray.size();i++){
                        JSONObject jsonObject = (JSONObject)  jsonArray.get(i);

                        String company = jsonObject.get("company").toString();
                        String model = jsonObject.get("model").toString();
                        String compressionRatio = jsonObject.get("Compression Ratio").toString();
                        String frontTyre =  jsonObject.get("Front tyre").toString();
                        String rearTyre =  jsonObject.get("Rear tyre").toString();
                        String gearbox = jsonObject.get("Gearbox").toString();
                        String frontSuspension = jsonObject.get("Front suspension").toString();
                        String rearSuspension = jsonObject.get("Rear suspension").toString();
                        String frontBrakes = jsonObject.get("Front brakes").toString();
                        String rearBrakes = jsonObject.get("Rear brakes").toString();
                        String category = jsonObject.get("Category").toString();
                        String engineType = jsonObject.get("Engine type").toString();
                        String fuelSystem = jsonObject.get("Fuel system").toString();
                        String coolingSystem = jsonObject.get("Cooling system").toString();
                        String overallWidthMm = jsonObject.get("Overall width mm").toString();
                        String overallLengthMm = jsonObject.get("Overall length mm").toString();
                        String seatHeightMm = jsonObject.get("Seat height mm").toString();
                        String wheelbaseMm = jsonObject.get("Wheelbase mm").toString();
                        String displacementCcm = jsonObject.get("Displacement ccm").toString();
                        String fuelCapacityLiters = jsonObject.get("Fuel capacity liters").toString();
                        String dryWeightKg = jsonObject.get("Dry weight kg").toString();
                        String overallHeightMm = jsonObject.get("Overall height mm").toString();

                        int powerBenchmarkRpm = intCheck(jsonObject.get("Power Benchmark RPM").toString());
                        int year = intCheck(jsonObject.get("Year").toString());
                        double torqueNm = doubleCheck(jsonObject.get("Torque Nm").toString());
                        double boreMm = doubleCheck(jsonObject.get("Bore mm").toString());
                        double powerHp = doubleCheck(jsonObject.get("Power HP").toString());
                        double strokeMm = doubleCheck(jsonObject.get("Stroke mm").toString());


                        BikeSpecEntity bikeSpecEntity = BikeSpecEntity.builder()
                                .company(company)
                                .model(model)
                                .year(year)
                                .powerHP(powerHp)
                                .powerBenchmarkRpm(powerBenchmarkRpm)
                                .boreMm(boreMm)
                                .strokeMm(strokeMm)
                                .torqueNm(torqueNm)
                                .compressionRatio(compressionRatio)
                                .overallHeightMm(overallHeightMm)
                                .overallWidthMm(overallWidthMm)
                                .seatHeightMm(seatHeightMm)
                                .wheelbaseMm(wheelbaseMm)
                                .overallLengthMm(overallLengthMm)
                                .dryWeightKg(dryWeightKg)
                                .fuelCapacityLiters(fuelCapacityLiters)
                                .frontTyre(frontTyre)
                                .rearTyre(rearTyre)
                                .gearbox(gearbox)
                                .frontSuspension(frontSuspension)
                                .rearSuspension(rearSuspension)
                                .frontBrakes(frontBrakes)
                                .rearBrakes(rearBrakes)
                                .category(category)
                                .engineType(engineType)
                                .fuelSystem(fuelSystem)
                                .coolingSystem(coolingSystem)
                                .displacementCcm(displacementCcm)
                                .build();

                        System.out.println("index : " + i);
                        bikeSpecRepository.save(bikeSpecEntity);


                        /**
                         * 리팩토링
                         * List -> Set 변경하여 중복 로직 제거
                         * add 로직 엔티티 내부로 옮김
                         */
                        BikeCompanyEntity bikeCompany = null;
                        if (!bikeCompanyRepository.existsByCompany(company)) {
                            bikeCompany = BikeCompanyEntity.createBikeCompany(company);
                            bikeCompanyRepository.save(bikeCompany);
                        }else{
                            bikeCompany = bikeCompanyRepository.findByCompany(company).orElse(null);
                        }

                        BikeModelEntity bikemodel = null;
                        if(!bikeModelRepository.existsByModelAndBikeCompany(model, bikeCompany)){
                            bikemodel = BikeModelEntity.createBikeModel(model, bikeCompany);
                            bikeModelRepository.save(bikemodel);
                        }else{
                            bikemodel = bikeModelRepository.findByModelAndBikeCompany(model, bikeCompany).orElse(null);
                        }

                        BikeYearEntity bikeYear = null;
                        if(!bikeYearRepository.existsByYearAndBikeModel(year, bikemodel)){
                            bikeYear = BikeYearEntity.createBikeYear(year, bikemodel);
                            bikeYearRepository.save(bikeYear);
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("read fail json file");
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                System.out.println("parsing failed");
                e.printStackTrace();
            }
        }

        System.out.println("data insert finish");

    }

    public static int intCheck(String word){
        if(word.equals("")){
            return 0;
        }else{
            return Integer.parseInt(word);
        }
    }

    public static double doubleCheck(String word){
        if(word.equals("")){
            return 0;
        }else{
            return Double.parseDouble(word);
        }
    }

}
