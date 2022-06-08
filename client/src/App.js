import './App.css';
import { useEffect, useState } from "react";
import Home from "./routes/Home";
import Header from "./routes/Header";
import Footer from "./routes/Footer";
import InsertPage from "./routes/InsertPage";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";

function App() {
  
  const style = {
    height: "100%",
    width: "100%",
  };

  useEffect(() => {
    const appName = "LiveRARY";
    document.title = appName;
  }, []);

  const [userName, setUserName] = useState("");
  const [showLoginModal, setShowLoginModal] = useState(false);

  const [userId, setUserId] = useState("");

  return (
    <div style={style}>
    <Router>
    <Header
        setUserName={setUserName}
        setShowLoginModal={setShowLoginModal}
        setUserId={setUserId}
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
        path="/insertPage"
        element={
          <InsertPage />
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