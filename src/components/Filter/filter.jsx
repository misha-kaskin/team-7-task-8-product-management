import { useState } from "react";

const Filter = ({ items }) => {
  const [activeType, setActiveType] = useState('Все');

  return (
    <div>
      <button>Все</button>
      <button>Мерч</button>
      <button>Ит-артефакт</button>
    </div>
  );
};

export default Filter;
