package utils;

public interface Observable<E> {
    /**
     * Register an observer.
     * @param o the observer
     */
    void addObserver(Observer<E> o);
    /**
     * Unregister an observer.
     * @param o the observer
     */
    void removeObserver(Observer<E> o);
    
    
    public void notifyObservers();
} 