package lapr.project.data;

import lapr.project.model.Place;
import lapr.project.stores.FreightNetwork;
import lapr.project.utils.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClossenessPlaces {
    private final int nPlaces;
    private final FreightNetwork network;
    private final List<Pair<Place,Double>> clossenessPlaces;
    private final Place p;

    public ClossenessPlaces(Place p ,int n){
        this.nPlaces = n;
        this.p = p;
        this.network = new FreightNetwork();
        this.clossenessPlaces = new ArrayList<>();
    }
    public void buildFreightNetworkFromDB() throws IOException {
        ConnectionFactory cf = new ConnectionFactory();
        this.network.buildFreightNetworkFromDatabase(cf.getDatabaseConnection(),this.nPlaces);
    }

    public void getAdjPlaces(){
        List<Place> list = new ArrayList<>();
        list.addAll(this.network.getGraph().adjVertices(p));
        for (Place place : list) {
            Pair<Place, Double> pair = new Pair<>(place, getDistance(place));
            this.clossenessPlaces.add(pair);
        }
    }
    public List<Pair<Place,Double>> getClossenessPlaces(){
        return this.clossenessPlaces;
    }

    public double getDistance(Place p1){
        return this.network.getGraph().edge(this.p, p1).getWeight();
    }

}
