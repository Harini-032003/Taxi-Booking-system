import java.util.*;

public class Booking{

 public static void bookTaxi(int customerId, char pickupPoint, char dropPoint, int pickupTime, List<Taxi> freeTaxis){
       int earning = 0;
       char nextSpot='Z';
       int nextFreeTime = 0;
       int min=999;
       int distBetweenPickUpandDrop=0;
       Taxi bookedTaxi= null;
       String tripDetail="";
       for(Taxi t: freeTaxis){
        int distBetweenCustomerandTaxi = Math.abs((t.currentSpot-'0')-(pickupPoint-'0'))*15;
        if(distBetweenCustomerandTaxi < min){
          bookedTaxi = t;
          distBetweenPickUpandDrop = Math.abs((dropPoint-'0')-(pickupPoint-'0'))*15;
          earning = (distBetweenPickUpandDrop-5)*10 + 100;
          nextSpot= dropPoint;
          int dropTime= pickupTime + distBetweenPickUpandDrop/15;
          nextFreeTime=dropTime;

          tripDetail = customerId + "               " + customerId + "          " + pickupPoint +  "      " + dropPoint + "       " + pickupTime + "          " +dropTime + "           " + earning;
          min = distBetweenCustomerandTaxi;
        }
       }
       bookedTaxi.setDetails(true, nextSpot, nextFreeTime, bookedTaxi.totalEarnings+earning, tripDetail);
       System.out.println("Taxi " + bookedTaxi.id + " booked successfully");
 }

  public static List<Taxi> createTaxis(int n){
    List<Taxi> taxis = new ArrayList<Taxi>();
    for(int i=1;i<=n;i++){
      Taxi taxi=new Taxi();
      taxis.add(taxi);
    }
    return taxis;
  }
  public static List<Taxi> GetfreeTaxis(List<Taxi> taxis, char pickupPoint, int pickupTime ){
     List<Taxi> freeTaxis= new ArrayList<Taxi>();
     for(Taxi t: taxis){
      if(t.freeTime <= pickupTime && (Math.abs((t.currentSpot-'0')-(pickupPoint-'0')) <= pickupTime - t.freeTime)){
        freeTaxis.add(t);
      }
     }
     return freeTaxis;
  }
  public static void main(String[] args){
    int id=1;
    Scanner scan= new Scanner(System.in);
    List<Taxi> taxis = createTaxis(4);
   
    
    while(true){
      System.out.println("0 -> Book Taxi");
      System.out.println("1 -> Print Taxi Details");
      int choice= scan.nextInt();

      switch (choice) {
        case 0:{
          int customerId = id;
          System.out.println("Enter pickup point");
          char pickupPoint = scan.next().charAt(0);
          System.out.println("Enter drop point");
          char dropPoint = scan.next().charAt(0);
          System.out.println("Enter pickup time");
          int pickupTime = scan.nextInt();

          if(pickupPoint < 'A' || pickupPoint > 'F' || dropPoint > 'F' || dropPoint <'A'){
             System.out.println("Valid pickup and drop are A, B, C, D, E, F. Exitting");
             return;
          }
          List<Taxi> freeTaxis = GetfreeTaxis(taxis, pickupPoint, pickupTime);
          if(freeTaxis.size() == 0){
            System.out.println("No free taxis available.Exitting");
            return;
          }
          Collections.sort(freeTaxis,(a,b) -> a.totalEarnings - b.totalEarnings);

          //get nearest taxi
          bookTaxi(id, pickupPoint, dropPoint, pickupTime, freeTaxis);
          id++;
          break;
      }
      case 1: 
      {
        //print taxi details for every taxi
        for(Taxi t: taxis){
          t.printTaxiDetails();
        }
        for(Taxi t: taxis){
          t.printDetails();
        }
        break;
      }
      
        default:
          return;
      }
    }
  }
}