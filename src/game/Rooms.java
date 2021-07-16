/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import structures.Network;
import java.util.Arrays;

/**
 * Classe que corresponde às divisões do mapa
 */
public class Rooms extends Map {

    private String room;
    private int ghost;
    private String[] links;
    private Network<String> map;

    /**
     * Metodo contrutor que cria uma intancia de um novo mapa
     */
    public Rooms() {
    }

    /**
     * Metodo contrutor que cria uma intancia de um novo mapa
     *
     * @param aposento divisao em que se encontra
     * @param fantasma valor do fantasma
     * @param ligacoes array de divisões que têm ligação com o aposento
     */
    public Rooms(String aposento, int fantasma, String[] ligacoes) {
        this.room = aposento;
        this.ghost = fantasma;
        this.links = ligacoes;
    }

    /**
     * Método que obtém o aposento
     *
     * @return aposento
     */
    public String getAposento() {
        return room;
    }

    /**
     * Método que atribui o aposento
     *
     * @param aposento
     */
    public void setAposento(String aposento) {
        this.room = aposento;
    }

    /**
     * Método que obtém os valores do fantasma
     *
     * @return valor do fantasma
     */
    public int getFantasma() {
        return ghost;
    }

    /**
     * Método que atribui os valores do fantasma
     *
     * @param fantasma valor do fantasma
     */
    public void setFantasma(int fantasma) {
        this.ghost = fantasma;
    }

    /**
     * Método que obtém as ligações com outras divisões
     *
     * @return array de ligações
     */
    public String[] getLigacoes() {
        return links;
    }

    /**
     * Método que atribui as ligações das divisões
     *
     * @param ligacoes array de ligações
     */
    public void setLigacoes(String[] ligacoes) {
        this.links = ligacoes;
    }

    /**
     * Método toString
     *
     * @return representação textual das divisões
     */
    @Override
    public String toString() {
        return "Aposentos{" + map
                + "\naposento = " + room
                + "\n, fantasma = " + ghost
                + "\n, ligacoes = " + Arrays.toString(links)
                + '}';
    }
}
