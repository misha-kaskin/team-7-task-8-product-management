const HeaderProfile = () => {
  if (localStorage.getItem("user")) {
    const userData = JSON.parse(localStorage.getItem("user"));

    return (
      <div>
        <div></div>
        <div>
          <div>{userData.name}</div>
          <div>
            <div>{userData.balance}</div>
            <button>Выйти</button>
          </div>
        </div>
      </div>
    );
  }
};

export default HeaderProfile;
