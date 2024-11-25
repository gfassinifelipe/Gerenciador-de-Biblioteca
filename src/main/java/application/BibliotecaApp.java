package application;

import model.Autor;
import model.Livro;

import javax.persistence.*;
import java.util.List;

public class BibliotecaApp{
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("BibliotecaPU");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            
            Autor autor1 = new Autor();
            autor1.setNome("Autor 1");
            
            Autor autor2 = new Autor();
            autor2.setNome("Autor 2");
            
            Livro livro1 = new Livro();
            livro1.setTitulo("Livro 1");
            livro1.getAutores().add(autor1); 
            livro1.getAutores().add(autor2);
            
            Livro livro2 = new Livro();
            livro2.setTitulo("Livro 2");
            livro2.getAutores().add(autor1);
            
            autor1.getLivros().add(livro1); 
            autor1.getLivros().add(livro2);
            autor2.getLivros().add(livro1);
            
            em.persist(autor1); 
            em.persist(autor2);
            em.persist(livro1); 
            em.persist(livro2);
            
            em.getTransaction().commit(); 
            
            List<Livro> livros = em.createQuery("FROM Livro", Livro.class).getResultList();
            for (Livro livro : livros) {
                System.out.println("Título: " + livro.getTitulo());
                System.out.println("Autores: ");
                for (Autor autor : livro.getAutores()) {
                    System.out.println(" - " + autor.getNome());
                }
            }
        } finally {
            em.close();
            emf.close();
        }
    }
}
