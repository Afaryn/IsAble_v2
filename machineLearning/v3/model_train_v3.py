import os
import tensorflow as tf
import matplotlib.pyplot as plt
from tensorflow.keras.preprocessing.image import ImageDataGenerator
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Conv2D, MaxPooling2D, Flatten, Dense
from callback import MyCallback
import tensorflow.lite as lite

def training_model():
    dataset = r'C:\Users\ramad\Downloads\sign_language\Citra BISINDO'

    # Build image data generator
    image_datagen = ImageDataGenerator(
        rescale = 1./255.,
        horizontal_flip = True,
        zoom_range = 0.2,
        shear_range = 0.2,
        validation_split = 0.3
    )

    # Make a train generator
    train_generator = image_datagen.flow_from_directory(
        dataset,
        target_size= (244, 244),
        batch_size= 20,
        class_mode='categorical',
        subset='training',
        color_mode='grayscale'
    )

    # Make a validation generator,
    valid_generator = image_datagen.flow_from_directory(
        dataset,
        target_size= (244, 244),
        batch_size= 20,
        class_mode='categorical',
        subset='validation',
        color_mode='grayscale'
    )

    # Build a model
    model = tf.keras.models.Sequential([
        tf.keras.layers.Conv2D(64, (3, 3), activation='relu', input_shape=(244, 244, 1)),
        tf.keras.layers.MaxPooling2D(2, 2),
        tf.keras.layers.Conv2D(64, (3, 3), activation='relu'),
        tf.keras.layers.MaxPooling2D(2, 2),
        tf.keras.layers.Conv2D(64, (3, 3), activation='relu'),
        tf.keras.layers.MaxPooling2D(2, 2),
        tf.keras.layers.Conv2D(128, (3, 3), activation='relu'),
        tf.keras.layers.MaxPooling2D(2, 2),
        tf.keras.layers.Flatten(),
        tf.keras.layers.Dropout(0.5),
        tf.keras.layers.Dense(512, activation='relu'),
        tf.keras.layers.Dense(26, activation='softmax')
    ])

    callback = MyCallback()

    model.compile(loss='categorical_crossentropy',
                  optimizer='adam',
                  metrics=['accuracy'])

    # Save training history
    history = model.fit(
        train_generator,
        validation_data=valid_generator,
        epochs=200,
        verbose=1,
        callbacks=callback
    )

    # Tampilkan dan simpan grafik histori pelatihan
    plot_and_save_history(history, "training_history.png")

    return model

def plot_and_save_history(history, filename):
    # Plot akurasi
    plt.plot(history.history['accuracy'], label='Train Accuracy')
    plt.plot(history.history['val_accuracy'], label='Validation Accuracy')
    plt.title('Training and Validation Accuracy')
    plt.xlabel('Epoch')
    plt.ylabel('Accuracy')
    plt.legend()
    plt.savefig(filename)
    plt.show()

    # Plot loss
    plt.plot(history.history['loss'], label='Train Loss')
    plt.plot(history.history['val_loss'], label='Validation Loss')
    plt.title('Training and Validation Loss')
    plt.xlabel('Epoch')
    plt.ylabel('Loss')
    plt.legend()
    plt.savefig(filename)
    plt.show()

if __name__ == '__main__':
    model = training_model()
    model.save("sign_language_model_v3.h5")

    # Convert the model to TensorFlow Lite format (.tflite)
    converter = lite.TFLiteConverter.from_keras_model(model)
    tflite_model = converter.convert()

    # Save the TensorFlow Lite model to a file
    with open("sign_language_model_v3.tflite", "wb") as f:
        f.write(tflite_model)