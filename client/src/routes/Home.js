import { useEffect, useState } from "react";
import styles from "./Home.module.css";
import Login from "./Login.js";

function Home({ showLoginModal, setShowLoginModal }){
    return (
        <div className={styles.home}>
        <p>This is Home.</p>
              {showLoginModal === true ? (
                <Login showLoginModal={true} setShowLoginModal={setShowLoginModal} />
              ) : null}

        <h1>This is LiveRARY !</h1>
        </div>
    );
}

export default Home;