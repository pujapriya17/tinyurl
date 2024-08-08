import { React, useState } from 'react';
import './ShortenedURLInput.css';

const serverAddress = 'http://localhost:8080/';

function ShortenedURLInput(props) {
  const [showResult, setShowResult] = useState(false);
  const [expiry, setExpiry] = useState("");
  const [longURL, setLongURL] = useState('');
  const [shortURL, setShortURL] = useState('');
  const [expiryDate, setExpiryDate] = useState('');

  const generateURL = async (longURL, expiry) => {
    setLongURL(longURL);
    await fetch(serverAddress + 'URL/shorter', {
      method: 'POST',
      body: JSON.stringify({
        longURL: longURL,
        validity: expiry
      }),
      headers: {
        'Content-type': 'application/json; charset=UTF-8'
      }
    })
      .then(response => {
        if (response.ok) {
          setShowResult(true);
          return response.json();
        } else {
          setShowResult(false);
          throw new Error("HTTP status: " + response.status);
        }
      })
      .then(data => {
        setShortURL(serverAddress + data['shortAlias']);
        setExpiryDate(data['expiresOn']);
      })
      .catch(err => {
        console.log(err.message);
      });
  };

  const handleSubmit = e => {
    e.preventDefault();
    console.log(e);
    generateURL(longURL, expiry);
  };

  return (
    <div className="main-section">
      <h1>Generate Short Link</h1>
      <fieldset>
        <form onSubmit={handleSubmit}>
          <label htmlFor="longURL">
            Enter Long URL*
          </label>
          <input
            type="text"
            name="longURL"
            id="longURL"
            value={longURL}
            placeholder="Enter URL"
            required
            onChange={e => {
              setLongURL(e.target.value)
            }}
          />
          <label htmlFor="expiry">
            Enter Expiry
          </label>
          <input
            type="number"
            name="expiry"
            id="expiry"
            value={expiry}
            placeholder="Enter expiry in years"
            onChange={e => {
              setExpiry(e.target.value)
            }}
          />
          <button
            type='submit'
            value="Submit"
          >
            Submit
          </button>
        </form>
      </fieldset>
      {showResult && (<div className="result">
        <p className='inline-div'>Alias generated : </p><a href={shortURL}>{shortURL}</a>
        <p>Valid till {expiryDate}.</p>
      </div>)}
    </div>
  );
}

export default ShortenedURLInput;