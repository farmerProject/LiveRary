import './App.css';
import { useEffect, useState } from "react";
import Home from "./routes/Home";
import Header from "./routes/Header";
import Footer from "./routes/Footer";
import Writer from "./routes/Writer";
import Viewer from "./routes/Viewer";
import SearchView from "./routes/SearchView";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import axios from "axios";

function App() {
  
  const style = {
    height: "100%",
    width: "100%",
  };
  const [userName, setUserName] = useState("");
  const [showLoginModal, setShowLoginModal] = useState(false);

  const [userId, setUserId] = useState("");
  const [userEmail, setUserEmail] = useState("");
  const [userIp, setUserIp] = useState("");

  useEffect(() => {
    const appName = "LiveRARY";
    document.title = appName;
  }, []);


  return (
    <div style={style}>
    <Router>
    <Header
        setUserName={setUserName}
        setShowLoginModal={setShowLoginModal}
        setUserId={setUserId}
        setUserEmail={setUserEmail}
        setUserIp={setUserIp}
    />
    <hr />
    <Routes>
      <Route
        path="/"
        element={
          <Home
              userName={userName}
              showLoginModal={showLoginModal}
              setShowLoginModal={setShowLoginModal}
          />
        }
      />
      <Route
        path="/writer"
        element={
          <Writer 
          userName={userName}
          userId={userId}
          userEmail={userEmail}
          userIp={userIp}
          />
        }
      />
      <Route
        path="/viewer"
        element={
          <Viewer 
          />
        }
      />
      <Route
        path="/searchView"
        element={
          <SearchView 
          userName={userName}
          showLoginModal={showLoginModal}
          setShowLoginModal={setShowLoginModal}
          />
        }
      />
    </Routes>
    <hr />
    <Footer />
  </Router>
  </div>
  );
}

export default App;