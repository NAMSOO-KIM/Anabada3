package com.example.anabada;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.soundcloud.android.crop.Crop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Product_regist extends AppCompatActivity {
   // private ImageView boardImageVIew;
     private FirebaseUser user;

    DatabaseReference mDatabase;
    private String boardPath; //사진 주소

    final String TAG = getClass().getSimpleName();
    ImageView imageView;
    Button cameraBtn;
    final static int TAKE_PICTURE = 1;
    String mCurrentPhotoPath;
    static final int REQUEST_TAKE_PHOTO = 3;
    static final int PICK_FROM_ALBUM = 2;
    Bitmap Bitimage;
    String mess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_regist);

        // 레이아웃과 변수 연결
        imageView = findViewById(R.id.imageView6);


        // 카메라 버튼에 리스터 추가
        //cameraBtn.setOnClickListener((View.OnClickListener) this);

        // 6.0 마쉬멜로우 이상일 경우에는 권한 체크 후 권한 요청
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "권한 설정 완료");
            } else {
                Log.d(TAG, "권한 설정 요청");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

        Button BoardRegisterButton = (Button) findViewById(R.id.board_register);
        BoardRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()){
                    case R.id.board_register:
                        boardUpdate();
                        break;
                }
                switch (v.getId()){
                    case R.id.board_register:
                        boardUpdate();
                        break;
                }


            }
        });

    }

    // 권한 요청
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult");
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
        }
    }



    // 카메라로 촬영한 영상을 가져오는 부분
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case TAKE_PICTURE:

                if (resultCode == Activity.RESULT_OK) { //Activity. <-으로 변경
                    mCurrentPhotoPath = intent.getStringExtra("mCurrentPhotoPath");
                    Bitmap bmp = BitmapFactory.decodeFile(mCurrentPhotoPath);
                    imageView.setImageBitmap(bmp);
                }
                break;
            case PICK_FROM_ALBUM:
                if(resultCode == RESULT_OK)
                {
                    try{
                        InputStream in = getContentResolver().openInputStream(intent.getData());
                        Bitmap img = BitmapFactory.decodeStream(in);
                        in.close();
                        imageView.setImageBitmap(img);
                    }catch(Exception e)
                    {
                    }
                }
                else if(resultCode == RESULT_CANCELED)
                {
                    Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
                }
                break;
            case REQUEST_TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    File file = new File(mCurrentPhotoPath);
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media
                                .getBitmap(getContentResolver(), Uri.fromFile(file));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (bitmap != null) {
                        ExifInterface ei = null;
                        try {
                            ei = new ExifInterface(mCurrentPhotoPath);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                                ExifInterface.ORIENTATION_UNDEFINED);

                        Bitmap rotatedBitmap = null;
                        switch(orientation) {

                            case ExifInterface.ORIENTATION_ROTATE_90:
                                rotatedBitmap = rotateImage(bitmap, 90);
                                break;

                            case ExifInterface.ORIENTATION_ROTATE_180:
                                rotatedBitmap = rotateImage(bitmap, 180);
                                break;

                            case ExifInterface.ORIENTATION_ROTATE_270:
                                rotatedBitmap = rotateImage(bitmap, 270);
                                break;

                            case ExifInterface.ORIENTATION_NORMAL:
                            default:
                                rotatedBitmap = bitmap;
                        }
                        Bitimage=rotatedBitmap;
                        imageView.setImageBitmap(rotatedBitmap);
                    }
                }
                break;
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.anabada.provider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }


    public void makeDialog(View view) {
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
        alt_bld.setTitle("사진 업로드").setCancelable(true).setPositiveButton("사진촬영",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.v("알림", "다이얼로그 > 사진촬영 선택");
                        // 사진 촬영 클릭
                        //Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        //startActivityForResult(cameraIntent, TAKE_PICTURE);
                        dispatchTakePictureIntent();
                        //takePhoto();
                    }
                }).setNeutralButton("앨범선택",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int id) {
                        Log.v("알림", "다이얼로그 > 앨범선택 선택");
                        Intent albumIntent = new Intent(Intent.ACTION_PICK);
                        albumIntent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                        startActivityForResult(albumIntent, PICK_FROM_ALBUM);
                        //앨범에서 선택
                        //selectAlbum();
                    }
                }).setNegativeButton("취소   ",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.v("알림", "다이얼로그 > 취소 선택");
                        // 취소 클릭. dialog 닫기.
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alt_bld.create();
        alert.show();
    }

    public void selectAlbum() {
        //앨범 열기
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    private void cropImage(Uri photoUri, File tempFile) {

        Log.d(TAG, "tempFile : " + tempFile);

        /**
         *  갤러리에서 선택한 경우에는 tempFile 이 없으므로 새로 생성해줍니다.
         */
        if(tempFile == null) {
            try {
                tempFile = createImageFile();
            } catch (IOException e) {
                Toast.makeText(this, "이미지 처리 오류! 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                finish();
                e.printStackTrace();
            }
        }

        //크롭 후 저장할 Uri
        Uri savingUri = Uri.fromFile(tempFile);

        Crop.of(photoUri, savingUri).asSquare().start(this);
    }

    public static byte[] imageToByteArray(String filePath)throws Exception{
        byte[] returnValue=null;

        ByteArrayOutputStream baos=null;
        FileInputStream fis=null;

        try {
            baos= new ByteArrayOutputStream();
            fis=new FileInputStream(filePath);

            byte[] buf=new byte[1024];
            int read=0;

            while ((read=fis.read(buf,0,buf.length))!=-1){
                baos.write(buf,0,read);
            }
            returnValue=baos.toByteArray();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if (baos!=null){
                baos.close();
            }
            if (fis!=null){
                fis.close();
            }
        }
        return returnValue;
    }

    //글 등록하기
    private void boardUpdate() {
        final String boardTitle = ((EditText) findViewById(R.id.boardTitle)).getText().toString();
        final String boardDescription = ((EditText) findViewById(R.id.boardDescription)).getText().toString();
        final String jpgName ="/"+ boardTitle+".jpg";
        if (boardTitle.length() > 0 && boardDescription.length() > 0) {

            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();
            user = FirebaseAuth.getInstance().getCurrentUser();
            //final StorageReference mountainImagesRef = storageRef.child("board/" + user.getUid() + "/boardImage.jpg");
            final StorageReference mountainImagesRef = storageRef.child("board/" + user.getUid() + jpgName);


            if (mCurrentPhotoPath == null) {
                Boardinfo boardinfo = new Boardinfo(user, boardTitle, boardDescription);
                uploader(boardinfo);
            } else {

                try {
                    InputStream stream = new FileInputStream(new File(mCurrentPhotoPath));
                    UploadTask uploadTask = mountainImagesRef.putStream(stream);
                    uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }
                            return mountainImagesRef.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Uri downloadUri = task.getResult();
                                FirebaseFirestore db = FirebaseFirestore.getInstance();
//                            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                Boardinfo boardinfo = new Boardinfo(user, boardTitle, boardDescription, downloadUri.toString());
                                db.collection("board").document(user.getUid()).set(boardinfo)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                startToast("게시글 등록을 성공하였습니다.");
                                                finish();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                startToast("게시글 등록에 실패하였습니다.");
                                                Log.w(TAG, "Error writing document", e);
                                            }
                                        });
                            } else {
                                Log.e("로그", "실패");
                            }
                        }
                    });
                } catch (FileNotFoundException e) {
                    Log.e("로그", "에러: " + e.toString());
                }
            }
        }else {
            startToast("게시글을 입력해주세요.");
        }
    }



    private void startToast(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
    private void uploader(Boardinfo boardinfo){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("board").document(user.getUid()).set(boardinfo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        startToast("게시글 등록을 성공하였습니다.");
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        startToast("게시글 등록에 실패하였습니다.");
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

    public void close(View view) {
        Intent intent =new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }
}


