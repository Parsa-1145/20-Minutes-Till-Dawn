package model.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EntityList extends ArrayList<Entity> {
    private final Map<Class<? extends Entity>, ArrayList<Entity>> devidedEntities = new HashMap<>();

    @Override
    public boolean add(Entity entity) {
        boolean b = super.add(entity);

        devidedEntities.putIfAbsent(entity.getClass(), new ArrayList<>());
        devidedEntities.get(entity.getClass()).add(entity);

        return b;
    }

    @Override
    public boolean remove(Object o) {
        if(contains(o)){
            boolean b = super.remove(o);
            if(o instanceof Entity entity){
                devidedEntities.putIfAbsent(entity.getClass(), new ArrayList<>());
                devidedEntities.get(entity.getClass()).remove(entity);
            }
            return b;
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public <T extends Entity> ArrayList<T> getEntitiesOfType(Class<T> clazz){
        devidedEntities.putIfAbsent(clazz, new ArrayList<>());
        return (ArrayList<T>) devidedEntities.get(clazz);
    }
}
