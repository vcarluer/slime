cd /usr/share/inkscape/extensions
echo "Deploying slime.in..."
sudo cp '/home/vince/gamersassociate/Slime/LevelDesign/Level editor/slime.inx' slime.in
echo "done"
echo "Deploying slime-level-compiler.py..."
sudo cp '/home/vince/gamersassociate/Slime/LevelDesign/Level editor/slime-level-compiler.py' slime-level-compiler.py
echo "done"
cd /usr/share/inkscape/templates
echo "Deploying Slime_level.svg..."
sudo cp '/home/vince/gamersassociate/Slime/LevelDesign/Level editor/Slime_level.svg' Slime_level.svg
echo "done"
echo "Enter to continue..."
read a
