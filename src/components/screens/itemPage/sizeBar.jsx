import { useState } from "react";
import styles from "./addItemPage.module.css";
import Select from "react-select";

const options = [
  {
    value: 1,
    label: "XS",
  },
  {
    value: 2,
    label: "S",
  },
  {
    value: 3,
    label: "M",
  },
  {
    value: 4,
    label: "L",
  },
  {
    value: 5,
    label: "XL",
  },
  {
    value: 6,
    label: "XXL",
  },
  {
    value: 7,
    label: "Универсальный",
  },
];

const SizeBar = ({ data, setItemData, size, amount }) => {
  const [currentValue, setCurrentValue] = useState(size+1);
  const [currentAmount, setCurrentAmount] = useState(amount);
  

  const getValue = () => {
    return currentValue ? options.find((c) => c.value === currentValue) : "";
  };

  const onChange = (newValue) => {
    setCurrentValue(newValue.value);
  };

  const handleAddSize = (e) => {
    setItemData((prev) => ({
      ...prev,
      sizes: [...data.sizes, +currentAmount]
    }))
    console.log(data.sizes);
    
  };

  return (
    <div className={styles.contentSize}>
      <div>
        <p className={styles.text}>Размер</p>
        <Select
          options={options}
          value={getValue()}
          onChange={onChange}
          placeholder="Выберите размер"
          className={styles.selector}
        />
      </div>
      <div>
        <p className={styles.textAmount}>Количество</p>
        <input
          onChange={(e) => {
            setCurrentAmount(e.target.value);
          }}
          onBlur={(e) => handleAddSize(e)}
          value={currentAmount}
          className={styles.amount}
          type="text"
        />
      </div>
    </div>
  );
};

export default SizeBar;
