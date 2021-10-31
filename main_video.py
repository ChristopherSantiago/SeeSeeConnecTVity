import cv2
import datetime
from simple_facerec import SimpleFacerec
import winsound
import time

# Encode faces from a folder
sfr = SimpleFacerec()
sfr.load_encoding_images("images/")

# Load Camera
cap = cv2.VideoCapture(0)
#print(cap.get(cv2.CAP_PROP_FRAME_WIDTH))
#print(cap.get(cv2.CAP_PROP_FRAME_HEIGHT))
cap.set(3, 3000)
cap.set(4, 3000)
print(cap.get(3))
print(cap.get(4))

while True:
    ret, frame = cap.read()
    ret, frame2 = cap.read()

    diff = cv2.absdiff(frame, frame2)
    gray = cv2.cvtColor(diff, cv2.COLOR_RGB2GRAY)
    blur = cv2.GaussianBlur(gray, (5, 5), 0)
    _, thresh = cv2.threshold(blur, 20, 255, cv2.THRESH_BINARY)
    dilated = cv2.dilate(thresh, None, iterations=3)
    datet = str(datetime.datetime.now())
    contours, _ = cv2.findContours(dilated, cv2.RETR_TREE, cv2.CHAIN_APPROX_SIMPLE)
    # cv2.drawContours(frame, contours, -1, (0, 255, 0), 2)

    for c in contours:
        if cv2.contourArea(c) < 1000:
            continue
        x, y, w, h = cv2.boundingRect(c)
        cv2.rectangle(frame, (x, y), (x+w, y+h), (225, 255, 225), 2)
        winsound.PlaySound('alert.wav', winsound.SND_ASYNC)

        

    font = cv2.FONT_HERSHEY_SIMPLEX
    datet = str(datetime.datetime.now())
    frame = cv2.putText(frame, datet, (5, 20), font, 0.5,(255,0,0), 2)


    # Detect Faces
    face_locations, face_names = sfr.detect_known_faces(frame)

    for face_loc, name in zip(face_locations, face_names):
        y1, x2, y2, x1 = face_loc[0], face_loc[1], face_loc[2], face_loc[3]

        cv2.putText(frame, name,(x1, y1 - 10), cv2.FONT_HERSHEY_DUPLEX, 1, (0, 0, 200), 2)
        cv2.rectangle(frame, (x1, y1), (x2, y2), (0, 0, 200), 4)
        
    cv2.imshow("Frame", frame)

    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

cap.release()
cv2.destroyAllWindows()
