from flask import Flask, render_template, request, jsonify, send_from_directory
import json
import os

app = Flask(__name__)

# Home route to serve the HTML file
@app.route('/')
def home():
    return render_template('index.html')

# Route to handle form submission
# @app.route('/submit', methods=['POST'])
# def submit():
#     # Get form data
#     name = request.form['name']
#     email = request.form['email']

#     # Perform database operation (e.g., store data in database)
#     # Here, we'll just print the data
#     print(f'Name: {name}, Email: {email}')

#     # Return a response
#     return 'Data submitted successfully!'

# with open("humans.json", 'r') as fp:
#     data = json.load(fp)

@app.route('/static/<path:filename>')
def serve_json(filename):
    with open(f'static/{filename}') as file:
        data = json.load(file)
    return jsonify(data)

@app.route('/images/<path:image_name>')
def serve_image(image_name):
    image_folder = os.path.join(app.root_path, 'images')
    return send_from_directory(image_folder, image_name)

@app.route('/api/files')
def get_files():
    folder_path = 'static/'  # Replace with the actual folder path
    
    files = os.listdir(folder_path)
    return jsonify(files)

if __name__ == '__main__':
    app.run(debug=True)