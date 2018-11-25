/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu;

import ohtu.verkkokauppa.Kauppa;
import ohtu.verkkokauppa.Pankki;
import ohtu.verkkokauppa.Tuote;
import ohtu.verkkokauppa.Varasto;
import ohtu.verkkokauppa.Viitegeneraattori;
import org.junit.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class verkkokauppaTest {

Kauppa k;
    
@Test
public void ostoksenPaaytyttyaPankinMetodiaTilisiirtoKutsutaanOikeillaParametreilla() {

    Pankki pankki = mock(Pankki.class);
    Viitegeneraattori viite = mock(Viitegeneraattori.class);
    
    when(viite.uusi()).thenReturn(42);

    Varasto varasto = mock(Varasto.class);

    when(varasto.saldo(1)).thenReturn(10); 
    when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

    Kauppa k = new Kauppa(varasto, pankki, viite);              

    k.aloitaAsiointi();
    k.lisaaKoriin(1);     
    k.tilimaksu("pekka", "12345");

    verify(pankki).tilisiirto(eq("pekka"), anyInt(), eq("12345"), anyString(),eq(5));   

}
@Test
public void kahdenEriTuotteenOstoksenJälkeenPankinMetodiaTilisiirtoKutsutaanOikeillaParametreilla() {

    Pankki pankki = mock(Pankki.class);
    Viitegeneraattori viite = mock(Viitegeneraattori.class);
    
    when(viite.uusi()).thenReturn(42);

    Varasto varasto = mock(Varasto.class);

    when(varasto.saldo(1)).thenReturn(10); 
    when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
    when(varasto.saldo(2)).thenReturn(5); 
    when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "olut", 4));

    Kauppa k = new Kauppa(varasto, pankki, viite);              

    k.aloitaAsiointi();
    k.lisaaKoriin(1);   
    k.lisaaKoriin(2);  
    k.tilimaksu("pekka", "12345");

    verify(pankki).tilisiirto(eq("pekka"), anyInt(), eq("12345"), anyString(),eq(9));   

}

@Test
public void kahdenSamanTuotteenOstoksenJälkeenPankinMetodiaTilisiirtoKutsutaanOikeillaParametreilla() {

    Pankki pankki = mock(Pankki.class);
    Viitegeneraattori viite = mock(Viitegeneraattori.class);
    
    when(viite.uusi()).thenReturn(42);

    Varasto varasto = mock(Varasto.class);

    when(varasto.saldo(1)).thenReturn(10); 
    when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

    Kauppa k = new Kauppa(varasto, pankki, viite);              

    k.aloitaAsiointi();
    k.lisaaKoriin(1);   
    k.lisaaKoriin(1);  
    k.tilimaksu("pekka", "12345");

    verify(pankki).tilisiirto(eq("pekka"), anyInt(), eq("12345"), anyString(),eq(10));   

}

@Test
public void kahdenEriTuotteenOstoksenJälkeenPankinMetodiaTilisiirtoKutsutaanOikeillaParametreillaKunToinenTuoteOnLoppunut() {

    Pankki pankki = mock(Pankki.class);
    Viitegeneraattori viite = mock(Viitegeneraattori.class);
    
    when(viite.uusi()).thenReturn(42);

    Varasto varasto = mock(Varasto.class);

    when(varasto.saldo(1)).thenReturn(10); 
    when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
    when(varasto.saldo(2)).thenReturn(0); 
    when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "olut", 4));

    Kauppa k = new Kauppa(varasto, pankki, viite);              

    k.aloitaAsiointi();
    k.lisaaKoriin(1);   
    k.lisaaKoriin(2);  
    k.tilimaksu("pekka", "12345");

    verify(pankki).tilisiirto(eq("pekka"), anyInt(), eq("12345"), anyString(),eq(5));   

}

@Test
public void metodinAloitaAsiointiKutsuminenNollaaAiemmanOstoksenTiedot() {

    Pankki pankki = mock(Pankki.class);
    Viitegeneraattori viite = mock(Viitegeneraattori.class);
    
    when(viite.uusi()).thenReturn(42);

    Varasto varasto = mock(Varasto.class);

    when(varasto.saldo(1)).thenReturn(10); 
    when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
    when(varasto.saldo(2)).thenReturn(5); 
    when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "olut", 4));

    Kauppa k = new Kauppa(varasto, pankki, viite);              

    k.aloitaAsiointi();
    k.lisaaKoriin(1);   
    k.aloitaAsiointi();
    k.lisaaKoriin(2);  
    k.tilimaksu("pekka", "12345");

    verify(pankki).tilisiirto(anyString(), anyInt(), anyString(), anyString(), eq(4));   

}

@Test
public void kauppaPyytääUudenViitenumeronJokaiselleMaksutapahtumalle() {

    Pankki pankki = mock(Pankki.class);
    Viitegeneraattori viite = mock(Viitegeneraattori.class);
    
    when(viite.uusi()).
            thenReturn(1).
            thenReturn(2).
            thenReturn(3).
            thenReturn(4);
            
            
    Varasto varasto = mock(Varasto.class);

    when(varasto.saldo(1)).thenReturn(10); 
    when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
    when(varasto.saldo(2)).thenReturn(5); 
    when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "olut", 4));

    Kauppa k = new Kauppa(varasto, pankki, viite);              

    k.aloitaAsiointi();
    k.lisaaKoriin(1);  
    k.tilimaksu("pekka", "12345");
    
    verify(pankki).tilisiirto(anyString(), eq(1), anyString(), anyString(), anyInt());
    
    k.aloitaAsiointi();
    k.lisaaKoriin(2);  
    k.tilimaksu("arto", "1652");

    verify(pankki).tilisiirto(anyString(), eq(2), anyString(), anyString(), anyInt());   

    k.aloitaAsiointi();
    k.lisaaKoriin(1);  
    k.lisaaKoriin(2);  
    k.tilimaksu("pekka", "12345");
    
    verify(pankki).tilisiirto(anyString(), eq(3), anyString(), anyString(), anyInt());
    
    k.aloitaAsiointi();
    k.lisaaKoriin(2);  
    k.lisaaKoriin(2);  
    k.tilimaksu("arto", "1652");

    verify(pankki).tilisiirto(anyString(), eq(4), anyString(), anyString(), anyInt()); 
}

@Test
public void koristaPoistetunTuotteenTiedotNollataan() {

    Pankki pankki = mock(Pankki.class);
    Viitegeneraattori viite = mock(Viitegeneraattori.class);
    
    when(viite.uusi()).thenReturn(42);

    Varasto varasto = mock(Varasto.class);

    when(varasto.saldo(1)).thenReturn(10); 
    when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
    when(varasto.saldo(2)).thenReturn(5); 
    when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "olut", 4));

    Kauppa k = new Kauppa(varasto, pankki, viite);              

    k.aloitaAsiointi();
    k.lisaaKoriin(1);   
    k.aloitaAsiointi();
    k.lisaaKoriin(2);  
    k.poistaKorista(1);
    k.tilimaksu("pekka", "12345");

    verify(pankki).tilisiirto(anyString(), anyInt(), anyString(), anyString(), eq(4));   

}



}
