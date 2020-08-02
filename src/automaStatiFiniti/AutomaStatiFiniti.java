package automaStatiFiniti;

import java.util.HashSet;

public interface AutomaStatiFiniti {
	public String id();
	public Stato statoAttuale();
	public boolean start();
	public HashSet<Transizione> transizioniAbilitate();
	public String esegui(Transizione t);
}
