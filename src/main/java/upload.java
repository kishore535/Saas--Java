

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.MultipartContent.Part;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.repackaged.com.google.appengine.api.search.SearchServicePb.IndexMetadata.Storage;
import com.google.apphosting.utils.remoteapi.RemoteApiPb.ApplicationError;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.protobuf.ByteString;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Servlet implementation class upload
 */

@WebServlet("/upload")
@MultipartConfig
public class upload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public upload() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		try 
		{
					
			//GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();
			//System.out.println(credentials);
			
			javax.servlet.http.Part filePart = request.getPart("image");
	        InputStream fileContent = filePart.getInputStream();

	        
			
			ByteString imageBytes = ByteString.readFrom(fileContent);
		    Image image = Image.newBuilder().setContent(imageBytes).build();
		    
		    
		    Feature feature = Feature.newBuilder().setType(Type.LABEL_DETECTION).build();
		    AnnotateImageRequest requestAnnotate =
		        AnnotateImageRequest.newBuilder().addFeatures(feature).setImage(image).build();
		    List<AnnotateImageRequest> requests = new ArrayList<>();
		    requests.add(requestAnnotate);
	
		    ImageAnnotatorClient client = ImageAnnotatorClient.create();
		    BatchAnnotateImagesResponse batchResponse = client.batchAnnotateImages(requests);
		    List<AnnotateImageResponse> imageResponses = batchResponse.getResponsesList();
		    //AnnotateImageResponse imageResponse = imageResponses.get(0);
	
		    StringBuilder responseTags = new StringBuilder();
		      for (AnnotateImageResponse res : imageResponses) {
		        if (res.hasError()) {
		          System.out.printf("Error: %s\n", res.getError().getMessage());
		          return;
		        }

		        for (EntityAnnotation annotation : res.getLabelAnnotationsList()) {
		          annotation
		              .getAllFields()
		              .forEach((k, v) -> responseTags.append("<td>" + v.toString() + "</td>\n"));
		          responseTags.append("</tr>\n<tr>");
		        }
		      }
		      response.setContentType("text/html");
		      PrintWriter out = response.getWriter();
		      out.println("<!doctype html>\n" + 
		      		"<html>\n" + 
		      		"   <body style=\"background-color:rgb(241, 245, 245);font-size: 20px;color:rgb(50, 128, 173);\"><center>\n" + 
		      		"      <table border = 1>\n" + 
		      		"          <caption style=\"font-size:50px;\">Detected labels</caption>\n" + 
		      		"          <tr>\n" + 
		      		"               <th> mid </th>\n" + 
		      		"               <th> Description </th>\n" + 
		      		"               <th> Score </th>\n" + 
		      		"               <th> Topicality </th>\n" + 
		      		"\n" + 
		      		"          </tr>\n" + 
		      		"\n" + 
		      		"        \n" + 
		      		"            <tr>\n" + 
		      		responseTags.toString() +
		      		"            </tr>\n" + 
		      		"         \n" + 
		      		"      </table>\n" + 
		      		"      </center>\n" + 
		      		"   </body>\n" + 
		      		"</html>");
		      
		      client.close();
		      
			
	
		    
	
		}
		catch (Exception ex)
		{
			System.out.println(ex);
		}

	}
	
	

}

