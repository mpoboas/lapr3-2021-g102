package lapr.project.model;

import lapr.project.utils.Pair;

import java.util.*;

public class CargoManifest {
    private final int id;
    private final Date elaborationDate;
    private final String type;
    private final List<Pair<Container,ContainerPosition>> containerList;
    public static class ContainerPosition{
        public final int x;
        public final int y;
        public final int z;
        public ContainerPosition(int x, int y, int z){
            this.x = x;
            this.y = y;
            this.z = z;
        }
        public String toString(){
            String p = "Containers position on ship\n";
            p = p + "x = " + this.x + "\n";
            p = p + "y = " + this.y + "\n";
            p = p + "z = " + this.z + "\n\n\n";
            return p;
        }
        @Override
        public boolean equals(Object o){
            if(o  instanceof ContainerPosition) {
                ContainerPosition cp = (ContainerPosition) o;
                return this.x == cp.x && this.y == cp.y && this.z == cp.z;
            }else{
                return false;
            }
        }
    }
    public CargoManifest (int id, Date elaborationDate, String type, List<Pair<Container,ContainerPosition>> containerList){
        this.id = id;
        this.elaborationDate = elaborationDate;
        this.type = type;
        this.containerList = containerList;
    }
    public int getId(){
        return this.id;
    }
    public Date getDate(){
        return this.elaborationDate;
    }
    public String getType(){
        return this.type;
    }
    public List<Pair<Container,ContainerPosition>> getContainerList(){
        return this.containerList;
    }

    public void addContainers(List<Pair<Container,ContainerPosition>> containerList){
        this.containerList.addAll(containerList);
    }
    public void addContainer(Pair<Container,ContainerPosition> container){
        this.containerList.add(container);

    }
    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append("ID Cargo Manifest:").append(this.id).append("\n");
        s.append("Eleboration date Cargo Manifest:").append(this.elaborationDate).append("\n");
        s.append("Cargo Manifest type:").append(this.type).append("\n");
        s.append("Container List and their position on ship\n");
        for (Pair<Container, ContainerPosition> containerContainerPositionPair : containerList) {
            s.append("Container ").append(containerContainerPositionPair.getFirst().getId()).append("\n").append(containerContainerPositionPair.getFirst()).append(containerContainerPositionPair.getSecond());
        }
        return s.toString();
    }




}
