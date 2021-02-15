import java.time.LocalDate;

public class Main {

    public static void main(String[] args) throws Exception {
        try {
            String fio;
            LocalDate birthDate;
            String birthDateStr;
            String sex;
            DB db = new DB();
            String res;
            switch (args[0]) {
                case "1":
                    DB.create();
                    break;
                case "2":
                    fio = args[1];
                    birthDateStr = args[2];
                    birthDate = LocalDate.parse(birthDateStr);
                    sex = args[3];
                    res = DB.add(fio, birthDate, sex);
                    System.out.println(res);
                    break;
                case "3":
                    res = DB.printUnique();
                    System.out.println(res);
                    break;
                case "4":
                    for(int i=0; i<1000000; i++) {
                        fio = DB.randomString();
                        birthDate = DB.createRandomDate(1970,2010);
                        sex = DB.randomSex();
                        DB.add(fio, birthDate, sex);
                    }
                    for(int i=0; i<100; i++) {
                        fio = DB.randomString();
                        fio = fio.replaceFirst("","F");
                        birthDate = DB.createRandomDate(1970,2010);
                        sex = DB.randomSex();
                        DB.add(fio, birthDate, sex);
                    }
                    System.out.println("People entered");
                    break;
                case "5":
                    long startTime = System.nanoTime();
                    res = DB.getF();
                    long endTime = System.nanoTime();
                    //System.out.println(res);
                    System.out.println("Duration " + (endTime-startTime) + " ns");
                    break;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
