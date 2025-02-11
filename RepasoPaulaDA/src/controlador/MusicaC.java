package controlador;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import modelo.Compositor;
import modelo.Estilo;
import modelo.Musica;

public class MusicaC {

	public ArrayList<Musica> obtenerTodasObras() {
		ArrayList<Musica> obras = new ArrayList<Musica>();
		SessionFactory sesion = HibernateUtil.getSessionFactory();
		Session session = sesion.openSession();
		String hql = "from Musica as m where m.compositor.naceCompo < '1970'";
		Query q = session.createQuery(hql);
		obras = (ArrayList<Musica>) q.list();

		return obras;
	}

	public ArrayList<Musica> obtenerObrasMozart() {
		ArrayList<Musica> obras = new ArrayList<Musica>();
		SessionFactory sesion = HibernateUtil.getSessionFactory();
		Session session = sesion.openSession();
		String hql = "from Musica as m where m.compositor.nomCompo = 'Wolfgang Amadeus MOZART' AND m.estilo.nomEstilo = 'Opera'";
//		String hql = "from Musica as m where LOWER(m.compositor.nomCompo) LIKE LOWER(MOZART) AND m.estilo.nomEstilo = 'Opera'";
		Query q = session.createQuery(hql);
		obras = (ArrayList<Musica>) q.list();

		return obras;
	}

//	public List<Compositor> buscarCompositorPorNombre(String nombre) {
//	    SessionFactory sesionFactory = HibernateUtil.getSessionFactory();
//	    Session session = sesionFactory.openSession();
//	    
//	    String hql = "FROM Compositor c WHERE LOWER(c.nomCompo) LIKE LOWER(:nombre)";
//	    Query query = session.createQuery(hql);
//	    query.setParameter("nombre", "%" + nombre + "%");
//
//	    List<Compositor> compositores = query.list();
//	    session.close();
//	    
//	    return compositores;
//	}

	public ArrayList<Musica> leerJsonMusica() {
		Gson gson = new Gson();
		ArrayList<Musica> obrakBerriak = null;

		try (FileReader reader = new FileReader("src/nuevasObras.json")) {
			JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();

			obrakBerriak = gson.fromJson(jsonArray, new TypeToken<ArrayList<Musica>>() {
			}.getType());

			for (JsonElement elemento : jsonArray) {
				JsonObject obj = elemento.getAsJsonObject();

				String descMusica = obj.get("DescMusica").getAsString();
				int compoId = obj.get("compoMusica").getAsInt();
				int estiloId = obj.get("estiloMusica").getAsInt();

				Compositor compositor = new Compositor();
				compositor.setIdCompo(compoId);

				Estilo estilo = new Estilo();
				estilo.setIdEstilo(estiloId);

				Musica musica = new Musica();
				musica.setDescMusica(descMusica);
				musica.setCompositor(compositor);
				musica.setEstilo(estilo);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return obrakBerriak;
	}
	
	public ArrayList<Musica> leerJsonMusicaCONTITULOENELJSON() {
		Gson gson = new Gson();
		ArrayList<Musica> obrakBerriak = null;

		try (FileReader reader = new FileReader("src/nuevasObras.json")) {
			//JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
			JsonObject rootObject = gson.fromJson(reader, JsonObject.class);
			JsonArray jsonArray = rootObject.getAsJsonArray("IKASTETXEAK");


			obrakBerriak = gson.fromJson(jsonArray, new TypeToken<ArrayList<Musica>>() {
			}.getType());

			for (JsonElement elemento : jsonArray) {
				JsonObject obj = elemento.getAsJsonObject();

				String descMusica = obj.get("DescMusica").getAsString();
				int compoId = obj.get("compoMusica").getAsInt();
				int estiloId = obj.get("estiloMusica").getAsInt();

				Compositor compositor = new Compositor();
				compositor.setIdCompo(compoId);

				Estilo estilo = new Estilo();
				estilo.setIdEstilo(estiloId);

				Musica musica = new Musica();
				musica.setDescMusica(descMusica);
				musica.setCompositor(compositor);
				musica.setEstilo(estilo);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return obrakBerriak;
	}

	public void insertarMusica(ArrayList<Musica> obrakBerriak) {
		SessionFactory sesion = HibernateUtil.getSessionFactory();
		Session session = sesion.openSession();

		for (Musica musica : obrakBerriak) {
			session.beginTransaction();
			session.save(musica);
			session.getTransaction().commit();
		}
	}

	public void borrarMusica(int id) {
		SessionFactory sesion = HibernateUtil.getSessionFactory();
		Session session = sesion.openSession();

		session.beginTransaction();
		Musica musica = session.get(Musica.class, id);
		session.delete(musica);
		session.getTransaction().commit();
	}

	public void modificarMusica(Musica musica) {
		SessionFactory sesion = HibernateUtil.getSessionFactory();
		Session session = sesion.openSession();

		session.beginTransaction();
		session.update(musica);
		session.getTransaction().commit();
	}

}
