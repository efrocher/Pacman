public abstract class Entity {

    // Attributs
    private float position[] = new float[2];

    // GetSet
    public float[] getPosition() {
        return position;
    }
    public void setPositionX(float x){
        position[0] = x;
    }
    public void setPositionY(float y){
        position[1] = y;
    }

    // Constructeurs
    public Entity (float xPos, float yPos){

        position[0] = xPos;
        position[1] = yPos;

    }

}
