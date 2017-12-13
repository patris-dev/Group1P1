package lazytown.source.game.actor;

import java.util.*;

/**
 * This class' main function is to keep a list of all active actors in our game, actors such as our character,
 * enemies as well as pickups and other props.
 */
public class Director {

    // The first list we make is a list of all of our active actors
    private final List<Actor> CURRENT_ACTORS;

    // This is the list of our actors that needs to be checked for collision
    private final List<Actor> COLLIDE_CHECKLIST;

    // This set is here so that we can remove actors from our CURRENT_ACTORS list
    private final Set<Actor> REMOVED_ACTORS;


    // Our constructor to start with, so that it's being initialized correctly
    public Director() {
        this.CURRENT_ACTORS = new ArrayList<Actor>();
        COLLIDE_CHECKLIST = new ArrayList<Actor>();
        REMOVED_ACTORS = new HashSet<Actor>();
    }

    /**
     * A getter method for our CURRENT_ACTOR List so we can access the data.
     */
    public List<Actor> getCurrentActors() { return CURRENT_ACTORS; }

    /**
     * We also need a method to update that list with new actors as they are being created.
     */
    public void addCurrentActors(Actor... actors){
        CURRENT_ACTORS.addAll(Arrays.asList(actors));
    }

    /**
     * As well as being able to remove from the list.
     */
    public void removeCurrentActors(Actor... actors) {
        CURRENT_ACTORS.removeAll(Arrays.asList(actors));
    }

    /**
     * We also need to be able to clear the list so we can repopulate it with new data.
     */
    public void resetCurrentActors() {CURRENT_ACTORS.clear();}

    /**
     * A getter method for our second list, so we can know what to run collision checks on.
     */
    public List getCollideChecklist(){ return COLLIDE_CHECKLIST; }


    /**
     * We need to be able to reset this list, as well as get new data points from our current list.
     */
    public void resetCollideChecklist() {
        COLLIDE_CHECKLIST.clear();
        COLLIDE_CHECKLIST.addAll(CURRENT_ACTORS);
    }

    /**
     * A getter method for returning the list of actors that we need to clean up.
     */
    public Set getRemovedActors() {return REMOVED_ACTORS;}

    /**
     * We need to be able to add to this list, as pickups are being picked up, or enemies die, etc.
     * In this method we check if there is more than one actor that needs to be removed, and depending on the result we
     * can add only one or we can add more. By knowing that there's only one and not looking to add more we are
     * presumably using less resources.
     */
    public void addToRemovedActors(Actor... actors) {
        if(actors.length > 1)
            REMOVED_ACTORS.addAll(Arrays.asList((Actor[]) actors));
        else
            REMOVED_ACTORS.add(actors[0]);
    }

    /**
     * We want to reset our list of removed actors, but before we do this we should make sure to remove the actors from
     * our current list.
     */
    public void resetRemovedActors(){
       CURRENT_ACTORS.removeAll(REMOVED_ACTORS);
       REMOVED_ACTORS.clear();
    }
}


