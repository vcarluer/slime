#!/usr/bin/env python

# Les deux lignes suivant sont necessaires seulement si le script n'est pas 
#  directement dans le dossier d'installation
import sys
#sys.path.append('/usr/share/inkscape/extensions')

# Utilisation du module inkex avec des effets predefinis
import inkex 
# Le module simplestyle fournit des fonctions pour le parsing des styles
from simplestyle import *

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
				self.__yReference = float(child.get("y"))
				self.__heightReference = float(child.get("height"))
				print  str(child.get("type"))+";"+str(child.get("x"))+";"+str(child.get("y"))+";"+str(child.get("width"))+";"+str(child.get("height"))
				return True;
			
			# Calculate new Y based on LevelInfo reference Y and height: Inverse items coordinate for GameItem, origin bottom left
			if child.get("height") is not None:
				y = self.__yReference + self.__heightReference - (float(child.get("y")) + float(child.get("height")))
			else:
				y = float(child.get("y"))

			# TimeAttack
			if str(child.get("type")) == 'TimeAttack':
				print  str(child.get("type"))+";"+str(child.get("x"))+";"+str(y)+";"+str(child.get("width"))+";"+str(child.get("height"))+";"+str(child.get("att_levelTime"))+";"+str(child.get("att_criticTime"))
				return True;
			
			# BecBunsen
			if str(child.get("type")) == 'BecBunsen':
				print  str(child.get("type"))+";"+str(child.get("x"))+";"+str(y)+";"+str(child.get("width"))+";"+str(child.get("height"))+";"+str(child.get("att_isOn"))+";"+str(child.get("att_delay"))+";"+str(child.get("id"))
				return True;

			# Button
			if str(child.get("type")) == 'Button':
				print  str(child.get("type"))+";"+str(child.get("x"))+";"+str(y)+";"+str(child.get("width"))+";"+str(child.get("height"))+";"+str(child.get("att_target"))+";"+str(child.get("att_resetTime"))
				return True;
				
			# Button
			if str(child.get("type")) == 'CircularSaw':
				print  str(child.get("type"))+";"+str(child.get("x"))+";"+str(y)+";"+str(child.get("width"))+";"+str(child.get("height"))+";"+str(child.get("id"))+";"+str(child.get("att_isOn"))
				return True;

			# Standard Item
			print  str(child.get("type"))+";"+str(child.get("x"))+";"+str(y)+";"+str(child.get("width"))+";"+str(child.get("height"))
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
