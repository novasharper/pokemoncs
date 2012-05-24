import os
import os.path as path
import xml.etree.ElementTree as ET
import hashlib

root_dir = path.join(path.expanduser('~'), "Dropbox", "Public", "PokemonResources")

def takeHash(f, block_size=2**10):
	f_path = path.join(root_dir, f)
	sha1 = hashlib.sha1()
	f = open(f_path, "rb")
	while True:
		data = f.read(block_size)
		if data == "":
			break
		sha1.update(data)
	return sha1.hexdigest()
	

def makeNode(root, f_path):
	node = ET.SubElement(root, "Node")
	type_l = "file"
	is_dir = path.isdir(path.join(root_dir, f_path))
	if is_dir: type_l = "dir"
	ET.SubElement(node, "type").text = type_l
	ET.SubElement(node, "path").text = f_path
	if not is_dir:
		file_h = takeHash(f_path)
		ET.SubElement(node, "hash").text = file_h
	

def main():
	root_e = ET.Element("FileList")
	for root, dirs, files in os.walk(path.join(root_dir, "resources")):
		rel_path = path.relpath(root, root_dir)
		makeNode(root_e, rel_path)
		for f in files:
			f_path = path.join(rel_path, f)
			makeNode(root_e, f_path)
	ET.ElementTree(root_e).write(path.join(root_dir, "index.xml"))

if __name__ == '__main__':
	try:
		main()
	except Exception as e:
		print e
	print "Done, press [return] to exit...."
	raw_input()

