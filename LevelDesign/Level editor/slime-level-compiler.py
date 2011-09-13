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
		i=0
		for child in root:
			i = i+1
			if child.get("type") is not None:
				if str(child.get("type")) == 'TimeAttack':
					print  str(child.get("type"))+";"+str(child.get("x"))+";"+str(child.get("y"))+";"+str(child.get("width"))+";"+str(child.get("height"))+";"+"50"
				else:
					print  str(child.get("type"))+";"+str(child.get("x"))+";"+str(child.get("y"))+";"+str(child.get("width"))+";"+str(child.get("height"))
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
