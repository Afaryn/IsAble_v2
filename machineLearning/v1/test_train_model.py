import os
import tensorflow as tf
from tensorflow.keras.preprocessing.image import ImageDataGenerator
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Conv2D, MaxPooling2D, Flatten, Dense


class myCallback(tf.keras.callbacks.Callback):
    def on_epoch_end(self, epoch, logs=None):
        if logs is None:
            logs = {}
        if logs.get('accuracy')>0.84 and logs.get('val_accuracy')>0.81:
            self.model.stop_training = True

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
        color_mode='grayscale',
        class_mode='categorical',
        subset='training'
    )

    # Make a validation generator,
    valid_generator = image_datagen.flow_from_directory(
        dataset,
        target_size= (244, 244),
        batch_size= 20,
        color_mode='grayscale',
        class_mode='categorical',
        subset='validation'
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

    callback = myCallback()

    model.compile(loss='categorical_crossentropy',
                  optimizer='adam',
                  metrics=['accuracy'])
    model.fit(
        train_generator,
        validation_data = valid_generator,
        epochs=20,
        verbose=1,
        callbacks=callback
    )

    return model



# Save the model as .h5 file
if __name__ == '__main__':
    model = training_model()
    model.save("sign_language_model_v1.h5")