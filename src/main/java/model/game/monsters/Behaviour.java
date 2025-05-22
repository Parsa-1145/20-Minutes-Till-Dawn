package model.game.monsters;

public abstract class Behaviour implements Cloneable{
    public abstract void update(float delta, Monster monster);

    @Override
    public Behaviour clone() {
        try {
            return (Behaviour) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // Shouldn't happen since we're Cloneable
        }
    }
}
