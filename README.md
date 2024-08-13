# TramSimulator-CS-sem2

🚋  Java project for very simplified simulation of urban traffic.

🚋  Vehicles can be various types, but each has a side number and its own line. Lines have their routes described in the data. 
    The route also includes the stop time at the terminal. A vehicle always travels the same route. 
    The vehicle travels its route in one direction, stops at the terminal, then returns (on the same route) and stops again at the terminal. 
    Trams are a type of vehicle. The first trams start their routes at 6 AM. After 11 PM, they only complete ongoing trips. 
    Trams of the same line are dispatched in the morning from both ends of the route: half from one end, half from the other. 
    Trams depart in equal intervals calculated as the round trip time (including terminal stops) divided by the number of trams on the route.
    Each tram has its own capacity, which is the same for all trams.

🚋  A tram line route consists of stops. Stops have their names and a common capacity (the same for all stops).
    We do not differentiate between stops in one direction or the other.

🚋  Each passenger lives near a certain stop. Every morning, at a random hour between 6 and 12 (the hour is randomly chosen each day),
    they go to their stop. If there are no available spots at the stop, they forgo traveling for the day. 
    Otherwise, they wait at the stop for a tram (any line, in either direction). When a tram arrives, they try to board. 
    If successful, they randomly choose one of the remaining stops on the route (in one direction), travel there, and attempt to disembark.
    If they fail, they continue riding, hoping to disembark at the next opportunity. Once they arrive at a stop, they start the same process again.

🚋  When a tram arrives at a stop, it first lets off passengers who want to disembark (some may not succeed if there are no free spots at the stop).
    The order of disembarking is arbitrary. Then, waiting passengers try to board the tram (again, some may not succeed if there 
    are no free spots on the tram).

🚋  The simulation runs for a specified number of days. At the end of each day of the simulation, all passengers return to their homes,
    and trams arrive at the appropriate ends of their routes.

🚋 PROGRAM OUTPUT:
  The program should output three things:
   * The parameters read.
   * A detailed log of the simulation, with one line for each event. Each line should start with the time of the event
     (day of simulation and hour) followed by its description.
   * Simulation statistics (number of trips, average waiting time at the stop, etc.).

🚋 PROGRAM INPUT:
  <Days of simulation (int)> 
  <Stop capacity (int)> 
  <Number of stops (int)> 
  for every stop:
    <Name of the stop (String)> 
  <Number of passengers (int)>
  <Tram capacity (int)>
  <Number of tram lines (int)>
  for every line: (lines numbered with consecutive natural numbers):
    <Number of trams at this line, route lenght (number of stops), (int, int)>
    line's route as a pair:
      <Stop name, travel time (string, int)>
