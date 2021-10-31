public class Gate extends GridElement{

    // Attributs
    private boolean isOpen;

    // GetSet

    // Constructeurs
    public Gate(boolean isOpen){
        super();
        this.isOpen = isOpen;
    }

    // Méthodes
    @Override
    public void onCrossed(Entity crossingEntity) {

    }
    @Override
    public boolean isCrosseable() {
        return isOpen;
    }
    public void swap(){
        isOpen = !isOpen;
    }

}
