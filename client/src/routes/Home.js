import { useEffect, useState } from "react";
import styles from "./Home.module.css";
import Login from "./Login.js";

function Home({ showLoginModal, setShowLoginModal }){
    return (
        <div className={styles.home + " container"}>
        <p>This is Home.</p>
              {showLoginModal === true ? (
                <Login showLoginModal={true} setShowLoginModal={setShowLoginModal} />
              ) : null}

        <h1>WOW !</h1>
        </div>
    );
}

export default Home;