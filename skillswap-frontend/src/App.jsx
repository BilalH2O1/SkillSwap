import React, { useState, useEffect } from 'react'
import './App.css';


function App() {
  const [listings, setListings] = useState([])
  const[user, setUser] = useState(null)
  const [error, setError] = useState(null) // New: Tracks errors

  // Form State
  const[title, setTitle] = useState('')
  const [description, setDescription] = useState('')
  const [price, setPrice] = useState('')
  const [category, setCategory] = useState('Technology')
  const [activeCategory, setActiveCategory] = useState('All')
  const [imageUrl, setImageUrl] = useState('')

  // 1. Fetch Data on Load (With Armor)
  useEffect(() => {
    fetch('http://localhost:8080/listing/all')
      .then(response => {
        if (!response.ok) throw new Error("Backend not responding")
        return response.json()
      })
      .then(data => {
        // Safety Check: Is it actually a list?
        if (Array.isArray(data)) {
          setListings(data)
        } else {
          console.error("Data is not a list:", data)
        }
      })
      .catch(err => {
        console.error('Fetch Error:', err)
        setError("Cannot connect to Backend (Is it running?)")
      });
    fetch('http://localhost:8080/user/1')
       .then(response => {
           if (!response.ok) throw new Error("Backend having problems (with users this time)")
           return response.json()
           })
       .then(data => {
           console.log("User Data: ", data)
           if (Array.isArray(data)) {
               setUser(data[0]);
               } else {
           setUser(data);
           }
           })
  }, [])

  // 2. Add Listing Function
  const handleAddListing = () => {
    const url = `http://localhost:8080/listing/add?userId=1&title=${encodeURIComponent(title)}&description=${encodeURIComponent(description)}&price=${encodeURIComponent(price)}&category=${encodeURIComponent(category)}&imageUrl=${encodeURIComponent(imageUrl)}`
//i added the encodeURIComponent becuz i am using image url which will probably cause an error becuz it has its own "?" and "&"
    fetch(url, { method: 'POST' })
      .then(res => res.json())
      .then(newListing => {
        setListings([...listings, newListing])
        setTitle('')
        setDescription('')
        setPrice('')
        setImageUrl('')
      })
  }

  // 3. Search Function
  const handleSearch = (selectedCategory) => {
      setActiveCategory(selectedCategory);
    let url = selectedCategory === 'All'
      ? 'http://localhost:8080/listing/all'
      : `http://localhost:8080/listing/search?category=${selectedCategory}`;

    fetch(url)
      .then(res => res.json())
      .then(data => setListings(Array.isArray(data) ? data : []))
  }
  const handleDelete = async(id) => {
      const isConfirmed = window.confirm("Are you sure you want to delete this listing?")
      if (!isConfirmed){
          return;
          }
      await fetch(`http://localhost:8080/listing/delete/${id}`, {method : 'DELETE'});
      setListings(listings.filter(item => item.id != id));

      }

  // 4. Buy Function
  const handleBuy = (id) => {
    fetch(`http://localhost:8080/listing/buy/${id}`, { method: 'POST' })
      .then(res => res.json())
      .then(updatedListing => {
         setListings(listings.map(item =>
           item.id === id ? updatedListing : item
         ));
      })
  }

  return (
    <div className = "all-page" >
      <strong><h2 className="balance"> ok </h2></strong>
      <h1>SkillSwap Marketplace</h1>
      <div className="user-balance-card">
            <span>My Balance:</span>
            {/* Show the balance with 2 decimals (e.g., $500.00) */}
            <strong className="balance-amount">
                ${user?.balance?.toFixed(2)}
            </strong>
        </div>


      {/* ERROR MESSAGE (If backend is down) */}
      {error && (
        <div>
          <strong>⚠️ Error:</strong> {error}
        </div>
      )}

      {/* THE FORM */}
      <div className="post-form">
        <h3>Post a New Gig</h3>
        <input placeholder="Title" value={title} onChange={e => setTitle(e.target.value)} />
        <input placeholder="Price" value={price} onChange={e => setPrice(e.target.value)} />
        <input placeholder="Desc" value={description} onChange={e => setDescription(e.target.value)}/>
        <input placeholder="Image Url (https://...)" value = {imageUrl} onChange={e => setImageUrl(e.target.value)}/>

        <select value={category} onChange={e => setCategory(e.target.value)} >
          <option value="Technology">Technology</option>
          <option value="Labor">Labor</option>
          <option value="Art">Art</option>
          <option value="Education">Education</option>
        </select>

        <button onClick={handleAddListing}>Post It!</button>
      </div>

      <h2>Available Gigs:</h2>

      {/* FILTERS */}
      <div className= "filter-container">
          <span className="filter-label">Filter:</span>
          <div className="filter-buttons">
          {['All', 'Technology', 'Labor', 'Art', 'Education'].map(cat => (
             <button key={cat} onClick={() => handleSearch(cat)} className={`filter-pill ${activeCategory === cat ? 'active' : ''}`}>{cat}</button>
          ))}
      </div> </div>

      {/* THE LIST */}
      {listings.length === 0 ? <div className = "empty-state"> No gigs found in this category </div> :
      <ul className= 'listings-grid'>
        {listings.map(listing => (
          <li key={listing.id} className={`listing-card ${listing.isSold ? 'sold' : ''}`} >
              {listing.imageUrl && (
                  <img
                      src={listing.imageUrl}
                      alt={listing.title}
                      className="card-image"
                  />
              )}
            <div className = 'card-header' >
            <strong >{listing.title}</strong>
            <span>${listing.price}</span>
            </div>

            <br />
            <span>{listing.description}</span>
            <br />
            <span>
              {listing.category}
            </span>


            {listing.isSold ? (
               <span className='sold-out'>SOLD OUT</span>
            ) : (
               <button onClick={() => handleBuy(listing.id)} className = 'buy-btn'>
                  Buy Now
               </button>

            )}
        <button className="delete-btn" onClick={() => handleDelete(listing.id)}>
            Delete
            </button>
        </li>
        ))}

      </ul>}
    </div>
  )
}

export default App