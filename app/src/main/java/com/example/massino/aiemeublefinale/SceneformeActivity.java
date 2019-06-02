package com.example.massino.aiemeublefinale;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.PixelCopy;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.ar.core.Anchor;
import com.google.ar.core.Frame;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.core.Trackable;
import com.google.ar.core.TrackingState;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.ArSceneView;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SceneformeActivity extends AppCompatActivity {
    private static final String TAG = "SceneformeActivity";
    private PointerDrawable pointer = new PointerDrawable();
    private boolean isTracking;
    private boolean isHitting;
    private ArFragment fragment;
    private ModelLoader modelLoader;
    private AnchorNode theAnchorNode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenforme);
        fragment = (ArFragment)
                getSupportFragmentManager().findFragmentById(R.id.sceneform_fragment);


        fragment.getArSceneView().getScene().addOnUpdateListener(frameTime -> {
            fragment.onUpdate(frameTime);
            onUpdate();
        });
        modelLoader = new ModelLoader(new WeakReference<>(this));

        initializeGallery();

       //****le bouton pour prendre la photo*****
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> takePhoto());

       //******le bouton pour supp un model 3D*********
        FloatingActionButton del = (FloatingActionButton) findViewById(R.id.del);
        del.setOnClickListener(view -> suppObj());
    }

    private void onUpdate() {
        boolean trackingChanged = updateTracking();
        View contentView = findViewById(android.R.id.content);
        if (trackingChanged) {
            if (isTracking) {
                contentView.getOverlay().add(pointer);
            } else {
                contentView.getOverlay().remove(pointer);
            }
            contentView.invalidate();
        }

        if (isTracking) {
            boolean hitTestChanged = updateHitTest();
            if (hitTestChanged) {
                pointer.setEnabled(isHitting);
                contentView.invalidate();
            }
        }
    }
    private boolean updateTracking() {
        Frame frame = fragment.getArSceneView().getArFrame();
        boolean wasTracking = isTracking;
        isTracking = frame != null &&
                frame.getCamera().getTrackingState() == TrackingState.TRACKING;
        return isTracking != wasTracking;
    }
    private boolean updateHitTest() {
        Frame frame = fragment.getArSceneView().getArFrame();
        android.graphics.Point pt = getScreenCenter();
        List<HitResult> hits;
        boolean wasHitting = isHitting;
        isHitting = false;
        if (frame != null) {
            hits = frame.hitTest(pt.x, pt.y);
            for (HitResult hit : hits) {
                Trackable trackable = hit.getTrackable();
                if (trackable instanceof Plane &&
                        ((Plane) trackable).isPoseInPolygon(hit.getHitPose())) {
                    isHitting = true;
                    break;
                }
            }
        }
        return wasHitting != isHitting;
    }
    private android.graphics.Point getScreenCenter() {
        View vw = findViewById(android.R.id.content);
        return new android.graphics.Point(vw.getWidth()/2, vw.getHeight()/2);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /*Reception des informations de l'intent de la recycler view pour voir quel objet afficher*/
     /*private int getIncomingIntent(){
        Log.d(TAG, "getIncomingIntent: checking for incoming intents.");
         int imageUrl=0;
        if(getIntent().hasExtra("image_url")){
            Log.d(TAG, "getIncomingIntent: found intent extras.");
            imageUrl = getIntent().getIntExtra("image_url",0);
            Log.d(TAG, "setImage: setting te image and name to widgets.");
            ImageView image = findViewById(R.id.image);
            Glide.with(this)
                    .asBitmap()
                    .load(imageUrl)
                    .into(image);
            //setImage(imageUrl);

        }
         return imageUrl;
    }*/
    private void initializeGallery() {
        int imageUrl = getIntent().getIntExtra("image_url",0);
        LinearLayout gallery = findViewById(R.id.gallery_layout);
        int obj =imageUrl;
        switch (obj) {
            case 0: {
                ImageView andy = new ImageView(this);
                andy.setImageResource(R.drawable.fauteilgris);
                andy.setContentDescription("fauteuil Créme");
                andy.setOnClickListener(view ->{addObject(Uri.parse("foteuil.sfb"));});
                gallery.addView(andy);

            }
            break;
            case 1: {
                ImageView andy = new ImageView(this);
                andy.setImageResource(R.drawable.banc);
                andy.setContentDescription("banc extérieur");
                andy.setOnClickListener(view ->{addObject(Uri.parse("Bench.sfb"));});
                gallery.addView(andy);

            }
            break;
            case 2 : {
                ImageView andy = new ImageView(this);
                andy.setImageResource(R.drawable.burau);
                andy.setContentDescription("bureau");
                andy.setOnClickListener(view ->{addObject(Uri.parse("bureau.sfb"));});
                gallery.addView(andy);

            }
            break;
            case 3 : {
                ImageView andy = new ImageView(this);
                andy.setImageResource(R.drawable.fauteuille3);
                andy.setContentDescription("Fauteuil une place");
                andy.setOnClickListener(view ->{addObject(Uri.parse("ff.sfb"));});
                gallery.addView(andy);

            }
            case 4 : {
                ImageView andy = new ImageView(this);
                andy.setImageResource(R.drawable.fauteuille1);
                andy.setContentDescription("Fauteuil bleu");
                andy.setOnClickListener(view ->{addObject(Uri.parse("foteuil2.sfb"));});
                gallery.addView(andy);

            }
            break;
            case 5 : {
                ImageView andy = new ImageView(this);
                andy.setImageResource(R.drawable.table);
                andy.setContentDescription("Table extérieur");
                andy.setOnClickListener(view ->{addObject(Uri.parse("table.sfb"));});
                gallery.addView(andy);

            }
            break;
            case 6 : {
                ImageView andy = new ImageView(this);
                andy.setImageResource(R.drawable.thor);
                andy.setContentDescription("Thor");
                andy.setOnClickListener(view ->{addObject(Uri.parse("thor.sfb"));});
                gallery.addView(andy);

            }
            break;
            default:Toast.makeText(getApplicationContext(),"rien n'a etai ressu",Toast.LENGTH_SHORT).show();

                break;

        }
    }


    //****************Ajout d'un objet******************

    private void addObject(Uri model) {
        Frame frame = fragment.getArSceneView().getArFrame();
        android.graphics.Point pt = getScreenCenter();
        List<HitResult> hits;
        if (frame != null) {
            hits = frame.hitTest(pt.x, pt.y);
            for (HitResult hit : hits) {
                Trackable trackable = hit.getTrackable();
                if (trackable instanceof Plane &&
                        ((Plane) trackable).isPoseInPolygon(hit.getHitPose())) {
                    modelLoader.loadModel(hit.createAnchor(), model);
                    break;

                }
            }
        }
    }
    public void addNodeToScene(Anchor anchor, ModelRenderable renderable) {
        AnchorNode anchorNode = new AnchorNode(anchor);
        TransformableNode node = new TransformableNode(fragment.getTransformationSystem());
        node.setRenderable(renderable);
        node.setParent(anchorNode);
        fragment.getArSceneView().getScene().addChild(anchorNode);
        node.select();
    }

    public void onException(Throwable throwable){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(throwable.getMessage())
                .setTitle("Codelab error!");
        AlertDialog dialog = builder.create();
        dialog.show();
        return;
    }

    //************supprimer un objet***************************************
    private void suppObj() {
        List<Node> nodeList = new ArrayList<>(fragment.getArSceneView().getScene().getChildren());
        for (Node childNode : nodeList) {
            if (childNode instanceof AnchorNode) {
                if (((AnchorNode) childNode).getAnchor() != null) {
                    ((AnchorNode) childNode).getAnchor().detach();
                    ((AnchorNode) childNode).setParent(null);
                }
            }
        }
    }

    //**********prendre une photo**************************
    private void saveBitmapToDisk(Bitmap bitmap, String filename) throws IOException {

        File out = new File(filename);
        if (!out.getParentFile().exists()) {
            out.getParentFile().mkdirs();
        }
        try (FileOutputStream outputStream = new FileOutputStream(filename);
             ByteArrayOutputStream outputData = new ByteArrayOutputStream()) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputData);
            outputData.writeTo(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException ex) {
            throw new IOException("Failed to save bitmap to disk", ex);
        }
    }
    private String generateFilename() {
        String date =
                new SimpleDateFormat("yyyyMMddHHmmss", java.util.Locale.getDefault()).format(new Date());
        return Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES) + File.separator + "Sceneform/" + date + "_screenshot.jpg";
    }
    private void takePhoto() {
        final String filename = generateFilename();
        ArSceneView view = fragment.getArSceneView();

        // Create a bitmap the size of the scene view.
        final Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),
                Bitmap.Config.ARGB_8888);

        // Create a handler thread to offload the processing of the image.
        final HandlerThread handlerThread = new HandlerThread("PixelCopier");
        handlerThread.start();
        // Make the request to copy.
        PixelCopy.request(view, bitmap, (copyResult) -> {
            if (copyResult == PixelCopy.SUCCESS) {
                try {
                    saveBitmapToDisk(bitmap, filename);
                } catch (IOException e) {
                    Toast toast = Toast.makeText(SceneformeActivity.this, e.toString(),
                            Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                        "Photo enregistré", Snackbar.LENGTH_LONG);
                snackbar.setAction("Ouvrir la galerie", v -> {
                    File photoFile = new File(filename);

                    Uri photoURI = FileProvider.getUriForFile(SceneformeActivity.this,
                            SceneformeActivity.this.getPackageName() + ".ar.codelab.name.provider",
                            photoFile);
                    Intent intent = new Intent(Intent.ACTION_VIEW, photoURI);
                    intent.setDataAndType(photoURI, "image/*");
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivity(intent);

                });
                snackbar.show();
            } else {
                Toast toast = Toast.makeText(SceneformeActivity.this,
                        "Failed to copyPixels: " + copyResult, Toast.LENGTH_LONG);
                toast.show();
            }
            handlerThread.quitSafely();
        }, new Handler(handlerThread.getLooper()));
    }

    }


