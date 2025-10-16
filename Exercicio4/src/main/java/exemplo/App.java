package exemplo;

import com.azure.ai.vision.face.FaceClient;
import com.azure.ai.vision.face.FaceClientBuilder;
import com.azure.ai.vision.face.models.FaceDetectionModel;
import com.azure.ai.vision.face.models.FaceDetectionResult;
import com.azure.ai.vision.face.models.FaceRectangle;
import com.azure.ai.vision.face.models.FaceRecognitionModel;
import com.azure.core.credential.KeyCredential;
import java.util.List;

public class App {

    private static final String SUBSCRIPTION_KEY = "";
    private static final String ENDPOINT = "";

    public static void main(String[] args) {
        FaceClient client = authenticate(ENDPOINT, SUBSCRIPTION_KEY);
        detectFacesFromUrl(client);
    }

    public static FaceClient authenticate(String endpoint, String key) {
        return new FaceClientBuilder()
            .endpoint(endpoint)
            .credential(new KeyCredential(key))
            .buildClient();
    }

    public static void detectFacesFromUrl(FaceClient faceClient) {
        System.out.println("======== DETECTAR FACES ========");
        

        String imageUrl = "https://raw.githubusercontent.com/Azure-Samples/cognitive-services-sample-data-files/master/Face/images/detection1.jpg";


        List<FaceDetectionResult> detectedFaces = faceClient.detect(
            imageUrl,
            FaceDetectionModel.DETECTION_03, 
            FaceRecognitionModel.RECOGNITION_04, 
            false
        );

        System.out.println("Detectadas " + detectedFaces.size() + " face(s) na imagem");

        int faceCount = 0;
        for (FaceDetectionResult face : detectedFaces) {
            faceCount++;
            FaceRectangle rect = face.getFaceRectangle();
            System.out.println("\n--- Face #" + faceCount + " ---");
            System.out.printf("Localização: Topo=%d, Esquerda=%d, Largura=%d, Altura=%d%n",
                rect.getTop(), rect.getLeft(), rect.getWidth(), rect.getHeight());
        }
    }
}