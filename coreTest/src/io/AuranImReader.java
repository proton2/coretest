/*
 *  try {
			AuranImReader v = new AuranImReader("C:/Programming/cube.im");
			v.load_im();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
 */

package io;

import java.io.IOException;
import java.io.RandomAccessFile;

public class AuranImReader {
	private static final int ID_SIZE = 4;
	private final RandomAccessFile file;
	byte [] arr;
	int file_size;
	
	public AuranImReader(String fileName) throws IOException{
		this.file = new RandomAccessFile(fileName, "r");
		file_size = (int)file.length();
		arr = new byte [file_size];
		file.read(arr);
		flush();
		close();
	}
	
	public void load_im() throws IOException{
		im_data im = new im_data();
		int pos = 0;
		
		im.id_file = byteToString(pos, ID_SIZE);
		if (!im.id_file.equals("JIRF"))
			throw new IOException("file is not Auran im");
		
		im.size_minus_id = byteToInt(pos);
		pos = 8;
		im.file_type = byteToString(pos, ID_SIZE);
		pos+=ID_SIZE;
		
		im.head_chunk = create_chunk(pos);
		
		System.out.println("chunks traversal Ok!");
	}
	
	Mesh create_mesh(int off){
		Mesh mesh = new Mesh();
		off+=ID_SIZE;
		
		mesh.size = byteToInt(off);
		off+=ID_SIZE;
		
		mesh.next_off = off + mesh.size;
		mesh.ver = byteToInt(off);
		off+=ID_SIZE;
		
		mesh.primitive_flags = byteToInt(off);
		off+=ID_SIZE;
		
		mesh.num_of_primitive_opt = byteToInt(off);
		off+=ID_SIZE;
		
		mesh.area = Float.intBitsToFloat(byteToInt(off));
		off+=ID_SIZE;
		
		mesh.vertex_count = byteToInt(off);
		off+=ID_SIZE;
		
		mesh.triangle_count = byteToInt(off);
		off+=ID_SIZE;
		
		mesh.index_count = byteToInt(off);
		off+=ID_SIZE;
		
		mesh.face_normal_count = byteToInt(off);
		off+=ID_SIZE;
		
		mesh.max_influences = byteToInt(off);
		off+=ID_SIZE;
		
		mesh.bone_name_size = byteToInt(off);
		off+=ID_SIZE;
		
		mesh.vertex_array = new Vertex[mesh.vertex_count];
		for (int i=0; i<mesh.vertex_count; i++){
			mesh.vertex_array[i] = new Vertex();
			mesh.vertex_array[i].x = Float.intBitsToFloat(byteToInt(off));
			off+=ID_SIZE;
			mesh.vertex_array[i].y = Float.intBitsToFloat(byteToInt(off));
			off+=ID_SIZE;
			mesh.vertex_array[i].z = Float.intBitsToFloat(byteToInt(off));
			off+=ID_SIZE;
			mesh.vertex_array[i].tx = Float.intBitsToFloat(byteToInt(off));
			off+=ID_SIZE;
			mesh.vertex_array[i].ty = Float.intBitsToFloat(byteToInt(off));
			off+=ID_SIZE;
		}
		
		mesh.triangles = new Polygon [mesh.triangle_count];
		
		for (int i=0; i<mesh.triangle_count; i++){
			mesh.triangles[i] = new Polygon();
			mesh.triangles[i].a = byteToHalfInt(off);
			off+=ID_SIZE/2;
			mesh.triangles[i].b = byteToHalfInt(off);
			off+=ID_SIZE/2;
			mesh.triangles[i].c = byteToHalfInt(off);
			off+=ID_SIZE/2;
		}
		
		for (int i=0; i<mesh.vertex_count; i++){
			mesh.vertex_array[i].nx = Float.intBitsToFloat(byteToInt(off));
			off+=ID_SIZE;
			mesh.vertex_array[i].ny = Float.intBitsToFloat(byteToInt(off));
			off+=ID_SIZE;
			mesh.vertex_array[i].nz = Float.intBitsToFloat(byteToInt(off));
			off+=ID_SIZE;
		}
		
		for (int i=0; i<mesh.triangle_count; i++){
			mesh.triangles[i].tnx = Float.intBitsToFloat(byteToInt(off));
			off+=ID_SIZE;
			mesh.triangles[i].tny = Float.intBitsToFloat(byteToInt(off));
			off+=ID_SIZE;
			mesh.triangles[i].tnz = Float.intBitsToFloat(byteToInt(off));
			off+=ID_SIZE;
		}
		
		return mesh;
	}
	
	Matl create_material(int off){
		Matl mtl = new Matl();
		off+=ID_SIZE;
		
		mtl.size = byteToInt(off);
		off+=ID_SIZE;
		
		mtl.next_off = off + mtl.size;
		mtl.ver = byteToInt(off);
		off+=ID_SIZE;
		
		mtl.name_size = byteToInt(off);
		off+=ID_SIZE;
		
		mtl.name = byteToString(off, mtl.name_size);
		off+=mtl.name_size;
		
		mtl.property_count = byteToInt(off);
		off+=ID_SIZE;
		
		mtl.two_sided = byteToInt(off);
		off+=ID_SIZE;
		
		mtl.opacity = Float.intBitsToFloat(byteToInt(off));
		off+=ID_SIZE;
		
		mtl.Ambient[0] = Float.intBitsToFloat(byteToInt(off));
		off+=ID_SIZE;
		mtl.Ambient[1] = Float.intBitsToFloat(byteToInt(off));
		off+=ID_SIZE;
		mtl.Ambient[2] = Float.intBitsToFloat(byteToInt(off));
		off+=ID_SIZE;
		
		mtl.Diffuse[0] = Float.intBitsToFloat(byteToInt(off));
		off+=ID_SIZE;
		mtl.Diffuse[1] = Float.intBitsToFloat(byteToInt(off));
		off+=ID_SIZE;
		mtl.Diffuse[2] = Float.intBitsToFloat(byteToInt(off));
		off+=ID_SIZE;
		
		mtl.Specular[0] = Float.intBitsToFloat(byteToInt(off));
		off+=ID_SIZE;
		mtl.Specular[1] = Float.intBitsToFloat(byteToInt(off));
		off+=ID_SIZE;
		mtl.Specular[2] = Float.intBitsToFloat(byteToInt(off));
		off+=ID_SIZE;
		
		mtl.Emissive[0] = Float.intBitsToFloat(byteToInt(off));
		off+=ID_SIZE;
		mtl.Emissive[1] = Float.intBitsToFloat(byteToInt(off));
		off+=ID_SIZE;
		mtl.Emissive[2] = Float.intBitsToFloat(byteToInt(off));
		off+=ID_SIZE;
		
		mtl.Shininess = Float.intBitsToFloat(byteToInt(off));
		off+=ID_SIZE;
		
		mtl.number_of_textures = byteToInt(off);
		off+=ID_SIZE;
		
		mtl.textures = create_textures(off, mtl.number_of_textures);
		
		return mtl;
	}
	
	Texture [] create_textures (int off, int count){
		Texture [] textures = new Texture [count];
		
		for (int i=0; i<count; i++){
			textures[i] = new Texture();
			
			textures[i].texture_type = byteToInt(off);
			off+=ID_SIZE;
			textures[i].texture_name_size = byteToInt(off);
			off+=ID_SIZE;
			textures[i].texture_file = byteToString(off, textures[i].texture_name_size);
			off+=textures[i].texture_name_size;
			textures[i].texture_amount = Float.intBitsToFloat(byteToInt(off));
			off+=ID_SIZE;
		}
		return textures;
	}
	
	Head_info create_head(int off){
		Head_info head = new Head_info();
		
		off+=ID_SIZE;
		head.pos[0] = Float.intBitsToFloat(byteToInt(off));
		off+=ID_SIZE;
		head.pos[1] = Float.intBitsToFloat(byteToInt(off));
		off+=ID_SIZE;
		head.pos[2] = Float.intBitsToFloat(byteToInt(off));
		off+=ID_SIZE;
		head.rot[0] = Float.intBitsToFloat(byteToInt(off));
		off+=ID_SIZE;
		head.rot[1] = Float.intBitsToFloat(byteToInt(off));
		off+=ID_SIZE;
		head.rot[2] = Float.intBitsToFloat(byteToInt(off));
		off+=ID_SIZE;
		head.rot[3] = Float.intBitsToFloat(byteToInt(off));
		off+=ID_SIZE;
		head.num_of_chunk_types = byteToInt(off);
		off+=ID_SIZE;
		head.Influence_per_Vertex = byteToInt(off);
		off+=ID_SIZE;
		head.Influence_per_Chunk = byteToInt(off);
		off+=ID_SIZE;
		
		head.box_min[0] = Float.intBitsToFloat(byteToInt(off));
		off+=ID_SIZE;
		head.box_min[1] = Float.intBitsToFloat(byteToInt(off));
		off+=ID_SIZE;
		head.box_min[2] = Float.intBitsToFloat(byteToInt(off));
		off+=ID_SIZE;
		
		head.box_max[0] = Float.intBitsToFloat(byteToInt(off));
		off+=ID_SIZE;
		head.box_max[1] = Float.intBitsToFloat(byteToInt(off));
		off+=ID_SIZE;
		head.box_max[2] = Float.intBitsToFloat(byteToInt(off));
		
		return head;
	}
	
	ChunkElement create_chunk(int off){
		ChunkElement chunk = new ChunkElement();
		chunk.id = byteToString(off, ID_SIZE);
		off+=ID_SIZE;
		
		chunk.size = byteToInt(off);
		chunk.next_off = off + ID_SIZE + chunk.size;
		
		off+=ID_SIZE;
		chunk.ver = byteToInt(off);
		
		if(chunk.id.equals("INFO"))
			chunk.head = create_head(off);
		
		off+=ID_SIZE;
		
		if(chunk.id.equals("CHNK")){
			chunk.index = byteToInt(off);
			off+=ID_SIZE;
			
			String s = byteToString(off, ID_SIZE);
			if(s.equals("MATL"))
				chunk.mtl = create_material(off);
				if (chunk.mtl != null)
					off=chunk.mtl.next_off;
			
			s = byteToString(off, ID_SIZE);
			if(s.equals("GEOM"))
				chunk.mesh = create_mesh(off);
		}
		
		if (chunk.next_off < file_size)
			chunk.next = create_chunk(chunk.next_off);
		
		if(chunk.id.equals("ATCH")){
			off+=ID_SIZE;
			if (off < file_size)
				chunk.attach = create_attachment(off);
		}
		
		return chunk;
	}
	
	Attachment create_attachment(int off){
		Attachment at = new Attachment();
		
		at.name_size = byteToInt(off);
		off+=ID_SIZE;
		at.name = byteToString(off, at.name_size);
		
		off+=at.name_size;
		
		at.or[0][0] = Float.intBitsToFloat(byteToInt(off));
		off+=ID_SIZE;
		at.or[0][1] = Float.intBitsToFloat(byteToInt(off));
		off+=ID_SIZE;
		at.or[0][2] = Float.intBitsToFloat(byteToInt(off));
		off+=ID_SIZE;
		
		at.or[1][0] = Float.intBitsToFloat(byteToInt(off));
		off+=ID_SIZE;
		at.or[1][1] = Float.intBitsToFloat(byteToInt(off));
		off+=ID_SIZE;
		at.or[1][2] = Float.intBitsToFloat(byteToInt(off));
		off+=ID_SIZE;
		
		at.or[2][0] = Float.intBitsToFloat(byteToInt(off));
		off+=ID_SIZE;
		at.or[2][1] = Float.intBitsToFloat(byteToInt(off));
		off+=ID_SIZE;
		at.or[2][2] = Float.intBitsToFloat(byteToInt(off));
		off+=ID_SIZE;
		
		at.posX = Float.intBitsToFloat(byteToInt(off));
		off+=ID_SIZE;
		at.posY = Float.intBitsToFloat(byteToInt(off));
		off+=ID_SIZE;
		at.posZ = Float.intBitsToFloat(byteToInt(off));
		off+=ID_SIZE;
		
		at.v = getVector(at.or);
		
		if (off < file_size)
			at.next = create_attachment(off);
		
		return at;
	}
	
	float[] getVector(float [][] matr){
		float x = 0, y = 0, z = 0;
		
        y=(float) Math.asin(matr[0][2]);
        if (y<Math.PI/2)
        {
        	 if (y > -Math.PI/2) 
        	 {
        		 x = (float)Math.atan2(-matr[1][2], matr[2][2]);
        		 z = (float)Math.atan2(-matr[0][1], matr[0][0]);
             }
        	 else
        	 {
        		 float value = (float) Math.atan2(matr[1][0], matr[1][1]);
        		 z = 0;
                 x = z - value;
        	 }
        }
        else
        {
        	float value = (float) Math.atan2(matr[1][0], matr[1][1]);
        	z = 0;
        	x = value - z; 
        }
        
        x=(float) Math.toDegrees(x)*-1;
        y=(float) Math.toDegrees(y)*-1;
        z=(float) Math.toDegrees(z)*-1;
        
		return new float [] {x, y, z};
	}
	
	String byteToString(int pos, int size){
		String res="";
		for (int i=pos; i<pos+size; i++){
			if(arr[i]!=0)
				res+=(char)arr[i];
		}
		return res;
	}
	
	int byteToInt(int off){
		/*
		int ch1 = arr[0];
	    int ch2 = arr[1];
	    int ch3 = arr[2];
	    int ch4 = arr[3];
	    
	    int a = arr[3];
	    a = a*256 + arr[2];
	    a = a*256 + arr[1];
	    a = a*256 + arr[0];
	    im.size_minus_id = a;
	    */
		int value = 0;
		for (int i=3+off; i>=off; i--) //for (int i = 0+off; i <= 3+off; i++)
	        value = (value << 8) + (arr[i] & 0xFF);
	    return value;
	}
	
	int byteToHalfInt(int off){
		int value = 0;
		for (int i=1+off; i>=off; i--)
	        value = (value << 8) + (arr[i] & 0xFF);
	    return value;
	}
	
	public byte[] intToByteArray(int a)
	{
	    byte[] ret = new byte[4];
	    ret[0] = (byte) (a & 0xFF);   
	    ret[1] = (byte) ((a >> 8) & 0xFF);
	    ret[2] = (byte) ((a >> 16) & 0xFF);
	    ret[3] = (byte) ((a >> 24) & 0xFF);
	    return ret;
	}
	
	public void flush() throws IOException {
        file.getChannel().force(true);
        file.getFD().sync();
    }

    public void close() throws IOException {
        file.close();
    }
}

class Matl{
	String name="";
	int size;
	int ver;
	int next_off;
	int name_size;
	int property_count;
	int two_sided;
	float opacity;
	
	float [] Ambient = {0,0,0};
	float [] Diffuse = {0,0,0};
	float [] Specular = {0,0,0};
	float [] Emissive = {0,0,0};
	float Shininess;
	int number_of_textures;
	Texture [] textures;
}

class Texture{
	int texture_type;
	int texture_name_size;
	String texture_file;
	float texture_amount;
}

class Mesh {
	int size;
	int ver;
	int next_off;
	int primitive_flags;
	int num_of_primitive_opt;
	float area;
	int vertex_count;
	int triangle_count;
	int index_count;
	int face_normal_count;
	int max_influences;
	int bone_name_size;
	Vertex [] vertex_array;
	Polygon [] triangles;
}

class Vertex {
	float x, y, z;
	float tx, ty, tz;
	float nx, ny, nz;
}

class Polygon{
	int a, b, c;
	float tnx;
	float tny;
	float tnz;	
}

class ChunkElement {
	ChunkElement next;
	String id="";
    int ver;
    int size;
    int next_off;
    int index;
    Attachment attach;
    Matl mtl;
    Head_info head;
    Mesh mesh;
}

class Head_info{
	float [] pos = {0,0,0};
	float [] rot = {0,0,0,0};
	int num_of_chunk_types;
	int Influence_per_Vertex;
	int Influence_per_Chunk;
	float box_min [] = {0,0,0};
	float box_max [] = {0,0,0};
}

class im_data{
	String id_file="";
	int size_minus_id;
	String file_type="";
	ChunkElement head_chunk;
	
	public im_data(){
		head_chunk = new ChunkElement();
	}
}

class Attachment{
	int name_size;
	String name = "";
	float or[][] = {{0,0,0}, {0,0,0}, {0,0,0}};
	float posX, posY, posZ;
	float [] v;
	Attachment next;
}
