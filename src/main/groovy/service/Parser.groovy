package service

import java.util.regex.Matcher
import java.util.regex.Pattern

class Parser {

    static def isNumber(def str) {
        try {
            Long.parseLong(str)
        } catch (Exception ex) {
            return false
        }
        return true
    }

    static List<String> parserAddress(String address, String compiler) {
        Pattern pattern = Pattern.compile(compiler)
        String[] data = pattern.split(address)
        return Arrays.asList(data)?.findAll { it }
    }

    static List<Pattern> getPatterns() {
        def patterns = []
        //пример г.Херсон ул.Лесная д.37
        patterns << Pattern.compile("^( |)+(г|Г|пгт|с|пос)([., ])+[А-Яа-яёЁ-]+([ .,])+(ул|пер|переулок|пр-кт)([., ])+[А-Яа-яёЁ-]+([., ])+(д|дом)([., ])+[0-9/А-Яа-я-]+( |,|\\.|)+\$")
        //пример г.Скадовск, ул. Александровская д 15 кв 17
        patterns << Pattern.compile("^( |)+(г|Г|пгт|с|пос)([., ])+[А-Яа-яёЁ-]+([ .,])+(просп|ш|пл|б-р|ул|пер|переулок|пр-кт)([., ])+[А-Яа-яёЁ-]+([., ])+(д|дом)([., ])+[0-9/А-Яа-я-]+([., ])+кв([., ])+[0-9]+( |,|\\.|)+\$")
        // пример Херсон, ул. Александровская д 15 кв 17
        patterns << Pattern.compile("^( |\\.|,|)+[А-Яа-яёЁ-]+([., ])+(просп|ш|пл|б-р|ул|пер|переулок|пр-кт)([., ])+[А-Яа-яёЁ-]+([., ])+(д|дом)(\\.|, | )+[0-9/А-Яа-я-]+([., ])+кв([., ])+[0-9]+( |,|\\.|)+\$")
        // пример Херсон, ул. Александровская д 15
        patterns << Pattern.compile("^( |\\.|,|)+[А-Яа-яёЁ-]+([ .,])+(просп|ш|пл|б-р|ул|пер|переулок|пр-кт)([., ])+[А-Яа-яёЁ-]+([., ])+(д|дом)([., ])+[0-9/А-Яа-я-]+( |,|\\.|)+\$")
        // пример Херсон, ул. Александровская, 15
        patterns << Pattern.compile("^( |\\.|,|)+[А-Яа-яёЁ-]+([ .,])+(просп|ш|пл|б-р|ул|пер|переулок|пр-кт)([., ])+[А-Яа-яёЁ-]+([., ])+[0-9/А-Яа-я-]+( |,|\\.|)+\$")
        // пример Херсон, ул. Александровская, 15, кв. 25
        patterns << Pattern.compile("^( |\\.|,|)+[А-Яа-яёЁ-]+([ .,])+(просп|ш|пл|б-р|ул|пер|переулок|пр-кт)([., ])+[А-Яа-яёЁ-]+([., ])+[0-9/А-Яа-я-]+([., ])+кв([., ])+[0-9]+( |,|\\.|)+\$")
        //пример г. Херсон, ул. Ворошилова, 29, кор.1, кв. 55
        patterns << Pattern.compile("^( |)+(г|Г|пгт|с|пос)( |\\.|,|)+[А-Яа-яёЁ-]+[ .,]+(просп|ш|пл|б-р|ул|пер|переулок|пр-кт)[., ]+[А-Яа-яёЁ-]+([., ])+[0-9/А-Яа-я-]+([., ])+(кор|корпус)[,. ]+[0-9]+[., ]+кв([., ])+[0-9]+( |,|\\.|)+\$")
        //пример ул.Чкалова 60 г.Каховка Херсонская обл.
        patterns << Pattern.compile("^( |)+(просп|ш|пл|б-р|ул|пер|переулок|пр-кт)[., ]+[А-Яа-яёЁ-]+([., ])+[0-9/А-Яа-я-]+([., ])+(г|Г|пгт|с|пос)([., ])+[А-Яа-яёЁ-]+([ .,])+[А-Яа-яёЁ-]+([ .,])+(обл|область)( |,|\\.|)+\$")
        //пример Полевая ул 32а Весёлое с Бериславский р-н Херсонская обл
        patterns << Pattern.compile("^( |)+[А-Яа-яёЁ-]+[ .,]+(просп|ш|пл|б-р|ул|пер|переулок|пр-кт)[., ]+[0-9/А-Яа-я-]+[., ]+[А-Яа-яёЁ]+[., ]+(г|Г|пгт|с|пос)[., ]+[А-Яа-яёЁ]+[., ]+р-н[., ]+[А-Яа-яёЁ]+[., ]+(обл|область)( |\\.|,|)+\$")
        //пример ул.Октябрьская 3 кв.52 г.Каховка Херсонская обл.
        patterns << Pattern.compile("^( |)+(просп|ш|пл|б-р|ул|пер|переулок|пр-кт)[., ]+[А-Яа-яёЁ-]+([., ])+[0-9/А-Яа-я-]+([., ])+кв([., ])+[0-9]+([., ])+(г|Г|пгт|с|пос)([., ])+[А-Яа-яёЁ-]+([ .,])+[А-Яа-яёЁ-]+([ .,])+(обл|область)( |,|\\.|)+\$")
        //пример Мира 14 Новороссийское с Скадовский р-н Херсонская обл
        patterns << Pattern.compile("^( |)+[А-Яа-яёЁ-]+[ .,]+[0-9/А-Яа-я-]+[., ]+[А-Яа-яёЁ]+[., ]+(г|Г|пгт|с|пос)[., ]+[А-Яа-яёЁ]+[., ]+р-н[., ]+[А-Яа-яёЁ]+[., ]+(обл|область)( |\\.|,|)+\$")
        //пример Заречная 11 Новореповка пос Новотроицкий р-н
        patterns << Pattern.compile("^( |)+[А-Яа-яёЁ-]+[ .,]+[0-9/А-Яа-я-]+[., ]+[А-Яа-яёЁ]+[., ]+(г|Г|пгт|с|пос)[., ]+[А-Яа-яёЁ]+[., ]+р-н( |\\.|,|)+\$")
        //пример Заречная 11 Новореповка пос
        patterns << Pattern.compile("^( |)+[А-Яа-яёЁ-]+[ .,]+[0-9/А-Яа-я-]+[., ]+[А-Яа-яёЁ]+[., ]+(г|Г|пгт|с|пос)( |\\.|,|)+\$")
        //пример Белозерский р-н, с. Токаревка ул, Молодежная 23
        patterns << Pattern.compile("^( |)+[А-Яа-яёЁ-]+[ .,]+р-н[., ]+(г|Г|пгт|с|пос)[., ]++[А-Яа-яёЁ-]+[., ]+(просп|ш|пл|б-р|ул|пер|переулок|пр-кт)[., ]+[А-Яа-яёЁ]+[., ]+[0-9/А-Яа-я-]+( |\\.|,|)+\$")
        //пример Белозерский р-н, с. Токаревка ул, Молодежная 23 кв 45
        patterns << Pattern.compile("^( |)+[А-Яа-яёЁ-]+[ .,]+р-н[., ]+(г|Г|пгт|с|пос)[., ]++[А-Яа-яёЁ-]+[., ]+(просп|ш|пл|б-р|ул|пер|переулок|пр-кт)[., ]+[А-Яа-яёЁ]+[., ]+[0-9]+[., ]+кв[., ]+[0-9/]+( |\\.|,|)+\$")
        //пример Заречная 11 кв 45 Новореповка пос Новотроицкий р-н
        patterns << Pattern.compile("^( |)+[А-Яа-яёЁ-]+[ .,]+[0-9/А-Яа-я-]+[., ]+кв([., ])+[0-9]+[., ]+[А-Яа-яёЁ]+[., ]+(г|Г|пгт|с|пос)[., ]+[А-Яа-яёЁ]+[., ]+р-н( |\\.|,|)+\$")
        //пример Победы пр-кт 8 кв 53 Новая Каховка
        patterns << Pattern.compile("^( |)+[А-Яа-яёЁ-]+[ .,]+(просп|ш|пл|б-р|ул|пер|переулок|пр-кт)[., ]+[0-9/А-Яа-я-]+[., ]+кв([., ])+[0-9]+[., ]+[А-Я][а-яё]+(([,. ]+[А-Я][а-яё]+( |\\.|,|)+)|(( |\\.|,|)+))\$")
        //пример Соборная 57 кв 41 Новая Каховка
        patterns << Pattern.compile("^( |)+[А-Яа-яёЁ-]+[ .,]+[0-9/А-Яа-я-]+[., ]+кв([., ])+[0-9]+[., ]+[А-Я][а-яё]+[,. ]+[А-Я][а-яё]+(([,. ]+[А-Я][а-яё]+( |\\.|,|)+)|(( |\\.|,|)+))\$")
        //пример Запорожская 7 кв.65 Степановка с.
        patterns << Pattern.compile("^( |)+[А-Яа-яёЁ-]+[ .,]+[0-9/А-Яа-я-]+[., ]+кв([., ])+[0-9]+[., ]+[А-Яа-яёЁ]+[., ]+(г|Г|пгт|с|пос)( |\\.|,|)+\$")
        //пример Херсон, Мира, 21, кв. 65
        patterns << Pattern.compile("^( |\\.|,|)+[А-Яа-яёЁ-]+([ .,])+[А-Яа-яёЁ-]+([., ])+[0-9/А-Яа-я-]+([., ])+кв([., ])+[0-9]+( |,|\\.|)+\$")
        //пример г.Херсон, ул. Черноморская 12, кв. 52
        patterns << Pattern.compile("^( |)+(г|Г|пгт|с|пос)( |\\.|,|)+[А-Яа-яёЁ-]+[ .,]+(просп|ш|пл|б-р|ул|пер|переулок|пр-кт)[., ]+[А-Яа-яёЁ-]+([., ])+[0-9/А-Яа-я-]+([., ])+кв([., ])+[0-9]+( |,|\\.|)+\$")

        return patterns
    }

    static List<Map<String, Object>> getAddressObject(List<String> addressList) {
        def result = []
        for (String address : addressList) {
            def addressObj = [
                    country          : "Россия",
                    region           : null,
                    regional_district: null,
                    locality_type    : null,
                    locality_name    : null,
                    road_type        : null,
                    road_name        : null,
                    house_number     : null,
                    house_symbol     : null,
                    apartment_number : null,
                    corpus           : null,
                    pattern          : null
            ]
            def patterns = getPatterns()
            if (address) {
                for (Pattern pattern : patterns) {
                    Matcher matcher = pattern.matcher(address)
                    def i = patterns.indexOf(pattern)
                    if (matcher.matches()) {
                        addressObj.pattern = i
                        //println("Pattern " + i + " - " + address + " : " + matcher.matches())
                        def addressAsList = parserAddress(address, ":|;|,|\\.| ")
                        switch (i) {
                            case 0:
                                addressObj.locality_name = addressAsList[1]
                                addressObj.road_type = addressAsList[2]
                                addressObj.road_name = addressAsList[3]
                                addressObj.house_number = addressAsList[5]
                                addressObj.locality_type = addressAsList[0]
                                break
                            case 1:
                                addressObj.locality_name = addressAsList[1]
                                addressObj.road_type = addressAsList[2]
                                addressObj.road_name = addressAsList[3]
                                addressObj.house_number = addressAsList[5]
                                addressObj.apartment_number = addressAsList[7]
                                break
                            case 2:
                            case 3:
                                addressObj.locality_name = addressAsList[0]
                                addressObj.road_type = addressAsList[1]
                                addressObj.road_name = addressAsList[2]
                                addressObj.house_number = addressAsList[4]
                                if (i == 2) {
                                    addressObj.apartment_number = addressAsList[6]
                                }
                                break
                            case 4:
                            case 5:
                                addressObj.locality_name = addressAsList[0]
                                addressObj.road_type = addressAsList[1]
                                addressObj.road_name = addressAsList[2]
                                addressObj.house_number = addressAsList[3]
                                if (i == 5) {
                                    addressObj.apartment_number = addressAsList[5]
                                }
                                break
                            case 6:
                                addressObj.locality_type = addressAsList[0]
                                addressObj.locality_name = addressAsList[1]
                                addressObj.road_type = addressAsList[2]
                                addressObj.road_name = addressAsList[3]
                                addressObj.house_number = addressAsList[4]
                                addressObj.corpus = addressAsList[6]
                                addressObj.apartment_number = addressAsList[8]
                                break
                            case 7:
                                addressObj.locality_type = addressAsList[3]
                                addressObj.locality_name = addressAsList[4]
                                addressObj.road_type = addressAsList[0]
                                addressObj.road_name = addressAsList[1]
                                addressObj.house_number = addressAsList[2]
                                addressObj.region = addressAsList[5]
                                break
                            case 8:
                                addressObj.locality_type = addressAsList[4]
                                addressObj.locality_name = addressAsList[3]
                                addressObj.road_type = addressAsList[1]
                                addressObj.road_name = addressAsList[0]
                                addressObj.house_number = addressAsList[2]
                                addressObj.regional_district = addressAsList[5]
                                addressObj.region = addressAsList[7]
                                break
                            case 9:
                                addressObj.locality_type = addressAsList[5]
                                addressObj.locality_name = addressAsList[6]
                                addressObj.road_type = addressAsList[0]
                                addressObj.road_name = addressAsList[1]
                                addressObj.house_number = addressAsList[2]
                                addressObj.apartment_number = addressAsList[4]
                                addressObj.region = addressAsList[7]
                                break
                            case 10:
                            case 11:
                            case 12:
                                addressObj.locality_type = addressAsList[3]
                                addressObj.locality_name = addressAsList[2]
                                addressObj.road_name = addressAsList[0]
                                addressObj.house_number = addressAsList[1]
                                if (i in [10, 11])
                                    addressObj.regional_district = addressAsList[4]
                                if (i == 10)
                                    addressObj.region = addressAsList[6]
                                break
                            case 13:
                            case 14:
                                //пример Белозерский р-н, с. Токаревка ул, Молодежная 23
                                addressObj.locality_type = addressAsList[2]
                                addressObj.locality_name = addressAsList[3]
                                addressObj.road_type = addressAsList[4]
                                addressObj.road_name = addressAsList[5]
                                addressObj.house_number = addressAsList[6]
                                addressObj.regional_district = addressAsList[0]
                                if (i == 14){
                                    addressObj.apartment_number = addressAsList[8]
                                }
                                break
                            case 15:
                            case 18:
                                addressObj.locality_type = addressAsList[5]
                                addressObj.locality_name = addressAsList[4]
                                addressObj.road_name = addressAsList[0]
                                addressObj.house_number = addressAsList[1]
                                addressObj.apartment_number = addressAsList[3]
                                if (i == 15) {
                                    addressObj.regional_district = addressAsList[6]
                                }
                                break
                            case 16:
                                if (addressAsList?.size() == 7) {
                                    addressObj.locality_name = addressAsList[5] + " " + addressAsList[6]
                                } else if (addressAsList?.size() == 6){
                                    addressObj.locality_name = addressAsList[5]
                                }
                                addressObj.road_name = addressAsList[0]
                                addressObj.road_type = addressAsList[1]
                                addressObj.house_number = addressAsList[2]
                                addressObj.apartment_number = addressAsList[4]
                                break
                            case 17:
                                if (addressAsList?.size() == 6) {
                                    addressObj.locality_name = addressAsList[4] + " " + addressAsList[5]
                                } else if (addressAsList?.size() == 5){
                                    addressObj.locality_name = addressAsList[4]
                                }
                                addressObj.road_name = addressAsList[0]
                                addressObj.house_number = addressAsList[1]
                                addressObj.apartment_number = addressAsList[3]
                                break
                            case 19:
                                addressObj.locality_name = addressAsList[0]
                                addressObj.road_name = addressAsList[1]
                                addressObj.house_number = addressAsList[2]
                                addressObj.apartment_number = addressAsList[4]
                                break
                            case 20:
                                addressObj.locality_type = addressAsList[0]
                                addressObj.locality_name = addressAsList[1]
                                addressObj.road_type = addressAsList[2]
                                addressObj.road_name = addressAsList[3]
                                addressObj.house_number = addressAsList[4]
                                addressObj.apartment_number = addressAsList[6]
                                break
                            default:
                                break
                        }
                        result << addressObj
                        break
                    }
                }
            }
        }
        return result
    }

    static List<Address> downloadRecordsFromFile(def recordNum) {
        List<List<String>> records = new ArrayList<>()
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("data-1667205364212.csv")))
            String line
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",\"")
                records.add(Arrays.asList(values))
                if (records.size() == recordNum) {
                    break
                }
            }
        } catch (IOException e) {
            println("Произошла ошибка во время чтения файла")
        }

        if (records.isEmpty() || records?.size() < 2) {
            println("В файле не обнаружены записи")
        }

        records.remove(0)

        def data = []

        records?.each { line ->
            Address addressObj = new Address()
            addressObj.id = line.size() > 0 ? line[0]?.replaceAll("\"", "") : null
            addressObj.registrationAddress = line.size() > 1 ? line[1]?.replaceAll("\"", "") : null
            addressObj.factAddress = line.size() > 2 ? line[2]?.replaceAll("\"", "") : null
            data << addressObj
        }
        return data
    }

    static def getFullAddressString(def card){
        def fullName = []
        if (card?.road_name && card?.road_type){
            fullName << card?.road_type + " " + card?.road_name
        }
        if (card?.house_number){
            fullName << "д. " +  card?.house_number + (card?.house_symbol ?: "")
        }
        if (card?.corpus){
            fullName << "корп. " + card?.corpus
        }
        if (card?.apartment_number){
            fullName << "кв. " + card?.apartment_number
        }
        if (card?.intracity_district){
            fullName << card?.intracity_district
        }
        if (card?.locality_name){
            fullName << (card?.locality_type + " " ?: "") + card?.locality_name
        }
        if (card?.regional_district){
            fullName << card?.regional_district
        }
        if (card?.region){
            fullName << card?.region
        }
        if (card?.country) {
            fullName << card?.country
        }

        return fullName?.join(", ")
    }

    static void main(String[] args) {
        def recNum = 200000
        List<service.Address> records = downloadRecordsFromFile(recNum)
        def result = getAddressObject(records?.collect{it?.registrationAddress})
//        result?.each{
//            println(getFullAddressString(it))
//        }
        for (int i = 0; i <= 19; i++){
            println("Паттерн "+ i +" сработал: " + result?.findAll{ it?.pattern == i }?.size())
        }

        println("Распарсило записей: " + result?.size() + ". " + (result.size()*100/recNum) + "% из " + recNum)
    }
}