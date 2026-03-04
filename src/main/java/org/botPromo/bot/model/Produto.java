package org.botPromo.bot.model;

import java.math.BigDecimal;

public class Produto {
    private String id;
    private String titulo;
    private String descricao;
    private BigDecimal preco;
    private String imagemUrl;
    private String linkAfiliado;
    private String dataPostagem;

    public Produto(){}

    public Produto (String id, String titulo, String descricao, String imagemUrl, BigDecimal preco, String linkAfiliado, String dataPostagem){
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.imagemUrl = imagemUrl;
        this.preco = preco;
        this.linkAfiliado = linkAfiliado;
        this.dataPostagem = dataPostagem;
    }

    public String getId(){
        return id;
    }
    public String getTitulo(){
        return titulo;
    }
    public String getDescricao(){
        return descricao;
    }
    public String getImagemUrl(){
        return imagemUrl;
    }
    public BigDecimal getPreco(){
        return preco;
    }
    public String getLinkAfiliado(){
        return linkAfiliado;
    }
    public String getDataPostagem(){
        return dataPostagem;
    }

    public void setId(String id){
        this.id = id;
    }
    public void setTitulo(String titulo){
        this.titulo = titulo;
    }
    public void setDescricao(String descricao){
        this.descricao = descricao;
    }
    public void setImagemUrl(String imagem){
        this.imagemUrl = imagem;
    }
    public void setPreco(BigDecimal preco){
        this.preco = preco;
    }
    public void setLinkAfiliado(String linkAfiliado){
        this.linkAfiliado = linkAfiliado;
    }
    public void setDataPostagem(String dataPostagem){
        this.dataPostagem = dataPostagem;
    }

    public String toString(){
        return "Produto{" +
                "id='" + id + '\'' +
                ", titulo='" + titulo + '\'' +
                ", preco=" + preco +
                '}';
    }
}
