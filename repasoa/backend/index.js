const express = require('express');
const mysql = require('mysql2');
const bodyParser = require('body-parser');
const cors = require('cors');


const app = express();
app.use(cors());
app.use(bodyParser.json());


const db = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: '',
    database: 'refugiogatitos',
    port: '3307' , 
});


db.connect((err) => {
    if (err) {
        console.error('Errorea datu-basera konektatzean:', err);
        return;
    }
    console.log('Datu-basera konektatuta');
});


app.get('/gatitos', (req, res) => {
    const query = 'SELECT * FROM gatitos';
    db.query(query, (err, results) => {
        if (err) throw err;
        res.send(results);
    });
});

app.get('/gatitos/:id', (req, res) => {
    const { id } = req.params;
    const query = 'SELECT * FROM gatitos WHERE id = ?';
    db.query(query, [id], (err, results) => {
        if (err) throw err;
        res.send(results[0]);
    });
});


app.post('/gatitos', (req, res) => {
    const newItem = req.body;
    const query = 'INSERT INTO gatitos SET ?';
    db.query(query, newItem, (err, results) => {
        if (err) throw err;
        res.send({ id: results.insertId, ...newItem });
    });
});


app.put('/gatitos/:id', (req, res) => {
    const { id } = req.params;
    const updatedItem = req.body;
    const query = 'UPDATE gatitos SET ? WHERE id = ?';
    db.query(query, [updatedItem, id], (err, results) => {
        if (err) throw err;
        res.send(results);
    });
});


app.delete('/gatitos/:id', (req, res) => {
    const { id } = req.params;
    const query = 'DELETE FROM gatitos WHERE id = ?';
    db.query(query, [id], (err, results) => {
        if (err) throw err;
        res.send(results);
    });
});



const PORT = 3001;
app.listen(PORT, () => {
    console.log(`Zerbitzaria http://localhost:${PORT}-n martxan dago`);	
});