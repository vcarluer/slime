#!/usr/bin/env python

# Les deux lignes suivant sont necessaires seulement si le script n'est pas 
#  directement dans le dossier d'installation
import sys
#sys.path.append('/usr/share/inkscape/extensions')

# Utilisation du module inkex avec des effets predefinis
import inkex 
# Le module simplestyle fournit des fonctions pour le parsing des styles
from simplestyle import *
import math

class Smile( inkex.Effect ):
	"""
	Exemple Inkscape 
	Cree un nouveau calque et dessine des elements de base
	"""
	__yReference = 0
	__heightReference = 0
	
	def __init__(self):

		# Appel du constructeur.
		inkex.Effect.__init__(self)


	def effect(self):
		"""
		Fonction principale
		Surchage la fonction de la classe de base
		Dessine quelques elements sur le docuement SVG
		"""
		# Recupere le document SVG principal
		svg = self.document.getroot()

		# Recuperation de la hauteur et de la largeur de la feuille
		width  = inkex.unittouu( svg.get('width') )
		height = inkex.unittouu( svg.attrib['height'] )
		
		# Creation d'un nouveau calque
		#root = inkex.etree.Element("MAP")
		root = self.getElementById("MAP")
		self.generateNodeAndChilds(root, True)
		
	# For now do only one level in xml tree with isRootMap
	def generateNodeAndChilds(self, root, isRootMap):
		self.printNode(root)
		if isRootMap == True:
			for child in root:
				self.generateNodeAndChilds(child, False)
	
	def printNode(self, child):
		if child.get("type") is not None:
			# LevelInfo
			if str(child.get("type")) == 'LevelInfo':
				self.__yReference = round(float(child.get("y")))
				self.__heightReference = round(float(child.get("height")))
				print  str(child.get("type"))+";"+str(child.get("x"))+";"+str(child.get("y"))+";"+str(child.get("width"))+";"+str(child.get("height"))+";0;"+str(child.get("att_maxDimension"))
				return True;
			
			
			x = float(str(child.get("x")))
			y = float(str(child.get("y")))
			if child.get("width") is not None:
				width = float(str(child.get("width")))
			else:
				width = float(0)

			if child.get("height") is not None:
				height = float(str(child.get("height")))
			else:
				height = float(0)
				
			transform = ""
			transform = str(child.get("transform"))
			angle = 0
			if transform is not None:
				if transform.startswith("matrix"):
					val1 = transform[7:]
					values = val1[:-1]
					splited = values.split(',')
					cosa = float(splited[0])
					sina = float(splited[1])
					angleRad = math.acos(cosa)
					if sina > 0:
						angle = math.degrees(angleRad)
					else:
						angle = - math.degrees(angleRad)
					tM = [[float(splited[0]), float(splited[2])], [float(splited[1]), float(splited[3])]]
					cX = float(x) + width / 2
					cY = float(y) + height / 2
					tX = tM[0][0]*cX + tM[0][1]*cY
					tY = tM[1][0]*cX + tM[1][1]*cY
					x = tX - width / 2
					y = tY - height / 2
				
				if transform.startswith("scale"):
					angle = 180
					x = - x - width
					y = -y - height
			
			x = round(x)
			y = round(y)
			width = round(width)
			height = round(height)
			angle = round(angle)

			# Calculate new Y based on LevelInfo reference Y and height: Inverse items coordinate for GameItem, origin bottom left
			if child.get("height") is not None:
				y = self.__yReference + self.__heightReference - (y + height)

			# TimeAttack
			if str(child.get("type")) == 'TimeAttack':
				print  str(child.get("type"))+";"+str(x)+";"+str(y)+";"+str(width)+";"+str(height)+";"+str(angle)+";"+str(child.get("att_levelTime"))+";"+str(child.get("att_criticTime"))
				return True;
			
			# BecBunsen
			if str(child.get("type")) == 'BecBunsen':
				print  str(child.get("type"))+";"+str(x)+";"+str(y)+";"+str(width)+";"+str(height)+";"+str(angle)+";"+str(child.get("att_isOn"))+";"+str(child.get("att_delay"))+";"+str(child.get("id"))
				return True;

			# Button
			if str(child.get("type")) == 'Button':
				print  str(child.get("type"))+";"+str(x)+";"+str(y)+";"+str(width)+";"+str(height)+";"+str(angle)+";"+str(child.get("att_target"))+";"+str(child.get("att_resetTime"))
				return True;
				
			# Button
			if str(child.get("type")) == 'CircularSaw':
				print  str(child.get("type"))+";"+str(x)+";"+str(y)+";"+str(width)+";"+str(height)+";"+str(angle)+";"+str(child.get("id"))+";"+str(child.get("att_isOn"))
				return True;

			# Standard Item
			print  str(child.get("type"))+";"+str(x)+";"+str(y)+";"+str(width)+";"+str(height)+";"+str(angle)
			return True;
	
	def affect(self, args=sys.argv[1:]):
		"""Affect an SVG document with a callback effect"""
		self.svg_file = args[-1]
		self.getoptions(args)
		self.parse()
		self.getposinlayer()
		self.getselected()
		self.getdocids()
		self.effect()

# Execute la fonction "effect" de la classe "CHello"
smile = Smile()
smile.affect()
