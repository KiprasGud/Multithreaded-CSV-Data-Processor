import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;


//             Map<String, Person2>   sarasas.stream()   //name+surname (2pirmos raides, person2 -> name, country, email, reikia tik visu male
class Zmogus2 {
    String vardas;
    String elektroninisPastas;
    String salis;

    Zmogus2(String vardas, String elektroninisPastas, String salis) {
        this.vardas = vardas;
        this.elektroninisPastas = elektroninisPastas;
        this.salis = salis;
    }
}

class Zmogus {
    Integer id;
    String vardas;
    String pavarde;
    String elektronisPastas;
    String lytis;
    String salis;
    String svetaine;
    Date gimimoData;

    Zmogus(String[] informacija) throws ParseException {
        this.id = Integer.parseInt(informacija[0]);
        this.vardas = informacija[1];
        this.pavarde = informacija[2];
        this.elektronisPastas = informacija[3];
        this.lytis = informacija[4];
        this.salis = informacija[5];
        this.svetaine = informacija[6];
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        this.gimimoData = format.parse(informacija[7]);
    }

    public String zmogausInfo() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return (id + " " + vardas + " " + pavarde + " " + elektronisPastas + " " + lytis + " " + salis + " " + svetaine + " " + format.format(gimimoData));
    }

    public String getVardPav() {
        return vardas.substring(0, 2) + pavarde.substring(0, 2);
    }

    public String getVardas() {
        return vardas;
    }

    public String getElektronisPastas() {
        return elektronisPastas;
    }

    public String getSalis() {
        return salis;
    }

    public Zmogus2 getZmogus() {
        return new Zmogus2(this.vardas, this.elektronisPastas, this.salis);
    }
}

class Duomenys implements Runnable {
    String failoPav;
    List<Zmogus> sarasas;

    Duomenys(String failoPav, List<Zmogus> sarasas) {
        this.failoPav = failoPav;
        this.sarasas = sarasas;
    }
    @Override
    public void run() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(failoPav));
            String eil = br.readLine();
            while ((eil = br.readLine()) != null) {
                String[] duom = eil.split(",");
                if (duom.length == 8) {
                    Zmogus zmog = new Zmogus(duom);
                    synchronized (sarasas) {
                        sarasas.add(zmog);
                    }
                }
            }
            br.close();
        }
        catch (Exception e) {
            System.out.println("Klaida nuskaitant duom: " + e.getMessage());
        }
    }
}

public class Main {
            static List<Zmogus> sarasas = Collections.synchronizedList(new ArrayList<>());
            static Map<String, Zmogus2> vyrSarasas = new HashMap<>();

            public static void main(String[] args) {

                uzkrautiDuom();

                while (true) {
                    System.out.println("Pasirinkite ka norite daryti");
                    System.out.println("1. Rodyti sarasa");
                    System.out.println("2. Filtruoti pagal gimimo data");
                    System.out.println("3. Rikiuoti pagal abecele");
                    System.out.println("4. Rikiuoti pagal ID");
                    System.out.println("5. Iseiti");

                    Scanner scanner = new Scanner(System.in);
                    while (!scanner.hasNextInt()) {
                        System.out.print("Iveskite skaiciu: ");
                        scanner.next();
                    }
                    int pas = scanner.nextInt();
                    if (pas == 1) {
                        rodytiSarasa();
                    } else if (pas == 2) {
                        filtPagalData();
                    } else if (pas == 3) {
                        filtAbecele();
                    } else if (pas == 4) {
                        filtSkaicius();
                    } else if (pas == 5) {
                        break;
                    } else {
                        System.out.println("Blogai ivedete");
                        vyruMap();
                    }
                }
            }


            public static void uzkrautiDuom() {
                Thread t1 = new Thread(new Duomenys("MOCK_DATA1.csv", sarasas));
                Thread t2 = new Thread(new Duomenys("MOCK_DATA2.csv", sarasas));
                Thread t3 = new Thread(new Duomenys("MOCK_DATA3.csv", sarasas));

                t1.start();
                t2.start();
                t3.start();

                try {
                    t1.join();
                    t2.join();
                    t3.join();
                } catch (InterruptedException e) {
                    System.out.println("Thread nepavyko pabaigti darbo " + e.getMessage());
                }
            }

            public static void vyruMap() {
                //System.out.println(sarasas);

                /* int sum = widgets.stream()
                 .filter(w -> w.getColor() == RED)
                 .mapToInt(w -> w.getWeight())
                 .sum();
In this example, widgets is a Collection<Widget>. We create a stream of Widget objects via Collection.stream(), filter it to produce a stream containing only the red widgets, and then transform it into a stream of int values representing the weight of each red widget. Then this stream is summed to produce a total weight.
In addition to Stream, which is a stream of object references, there are primitive specializations for IntStream, LongStream, and DoubleStream, all of which are referred to as "streams" and conform to the characteristics and restrictions described here.
To perform a computation, stream operations are co

                 */


                //List<Zmogus> vyr = sarasas.stream().filter(zmogus -> zmogus.lytis.equals("Male"))

                //vyrSarasas = sarasas.stream().map(z -> z.lytis.equals("Male")).mapMulti(z -> z.);

                //vyrSarasas = sarasas.stream().collect(Collectors.toMap(zmogus -> {zmogus -> }, Zmogus::getZmogus));
                //List<Zmogus> vyr = sarasas.stream().filter(z -> z.lytis.equals("Male")).toList();
                //List<Zmogus2> zmogVyr = new ArrayList<>();

                //for (Zmogus z : vyr) {
                 //   vyrSarasas.put(z.getVardPav(), z.getZmogus());
               // }
                Map<String, Zmogus2> vyrSarasas1 = new HashMap<>();
                for (Zmogus z : sarasas) {
                    if ("Male".equals(z.lytis)) {
                        vyrSarasas1.put(z.getVardPav(), z.getZmogus());
                    }
                }


                System.out.println("Vyrų skaičius: " + vyrSarasas1.size());


                Map<String, Zmogus2> vyrSarasas = sarasas.stream()
                        .filter(z -> "Male".equals(z.lytis))

                        .collect(Collectors.toMap(Zmogus::getVardPav, Zmogus::getZmogus, (existing, replacement) -> replacement));


                //vyrSarasas = sarasas.stream().filter(z -> "Male".equals(z.lytis)).collect(Collectors.toMap(Zmogus::getVardPav,Zmogus::getZmogus));


                //vyrSarasas.forEach(vyr -> System.out.println(vyr));

                for (Map.Entry<String, Zmogus2> entry : vyrSarasas.entrySet()) {
                    //Zmogus2 z2 = entry.getValue();
                    //String fn = entry.getKey() + " " +z2.vardas + " " + z2.salis + " " + z2.elektroninisPastas;

                    //System.out.printf("%-15s : %s%n", entry.getKey(), entry.getValue().vardas );
                    System.out.println(entry.getKey() + " " + entry.getValue().vardas + " " + entry.getValue().salis + " " + entry.getValue().elektroninisPastas);
                }

                        //new Zmogus2(Zmogus::getVardas, Zmogus::getSalis, Zmogus::getElektronisPastas)
                //vyr.stream().map(zmogus -> )
                //Map<String, Person2>   sarasas.stream()   //name+surname (2pirmos raides, person2 -> name, country, email, reikia tik visu male
                 //       .
            }


            public static void rodytiSarasa() {
                sarasas.stream().forEach(zmogus -> System.out.println(zmogus.zmogausInfo()));
            }

            public static void filtPagalData() {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Iveskite pirma data foramtas(mmmm-mm-dd)");
                String pirmaData = scanner.nextLine();
                System.out.println("Iveskite antra data");
                String antraData = scanner.nextLine();

                try {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Date pData = format.parse(pirmaData);
                    Date aData = format.parse(antraData);

                    List<Zmogus> filtData = sarasas.stream().filter(zmogus -> !zmogus.gimimoData.before(pData) && !zmogus.gimimoData.after(aData)).toList();

                    filtData.stream().forEach(zmogus -> System.out.println(zmogus.zmogausInfo()));
                } catch (Exception e) {
                    System.out.println("Klaida " + e.getMessage());
                }

            }

            public static void filtAbecele() {
                System.out.println("Pasirininkite pagal ka rikiuoti");
                System.out.println("1. Vardas nuo didziausio");
                System.out.println("2. Vardas nuo maziausio");
                System.out.println("3. Pavarde nuo didziausio");
                System.out.println("4. Pavarde nuo maziausio");
                Scanner scanner = new Scanner(System.in);
                while (!scanner.hasNextInt()) {
                    System.out.print("Iveskite skaiciu: ");
                    scanner.next();
                }
                int pas = scanner.nextInt();

                List<Zmogus> filt;

                if (pas == 1) {
                    filt = sarasas.stream().sorted((z1,z2)-> z1.vardas.compareTo(z2.vardas)).toList();
                } else if (pas == 2) {
                    filt = sarasas.stream().sorted((z1,z2)-> z1.vardas.compareTo(z2.vardas)).toList().reversed();
                } else if (pas == 3) {
                    filt = sarasas.stream().sorted((z1,z2)-> z1.pavarde.compareTo(z2.pavarde)).toList();
                } else if (pas == 4) {
                    filt = sarasas.stream().sorted((z1,z2)-> z1.pavarde.compareTo(z2.pavarde)).toList().reversed();
                } else {
                    System.out.println("Blogai ivedete");
                    return;
                }

                filt.stream().forEach(zmogus -> System.out.println(zmogus.zmogausInfo()));
            }

            public static void filtSkaicius() {
                System.out.println("1. Nuo maziausio iki didziausio");
                System.out.println("2. Nuo didziausio iki maziausio");

                Scanner scanner = new Scanner(System.in);
                while (!scanner.hasNextInt()) {
                    System.out.print("Iveskite skaiciu: ");
                    scanner.next();
                }
                int pas = scanner.nextInt();

                List<Zmogus> filt;

                if  (pas == 1) {
                    filt = sarasas.stream().sorted((z1,z2)-> z1.id.compareTo(z2.id)).toList();
                } else if (pas == 2) {
                    filt = sarasas.stream().sorted((z1,z2)-> z1.id.compareTo(z2.id)).toList().reversed();
                } else {
                    System.out.println("Blogai ivedete");
                    return;
                }

                filt.stream().forEach(zmogus -> System.out.println(zmogus.zmogausInfo()));
            }

}