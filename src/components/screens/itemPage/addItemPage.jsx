import { useParams } from "react-router-dom";
import styles from "./addItemPage.module.css";
import { useEffect, useRef, useState } from "react";
import Select from "react-select";
import ItemService from "../../../services/item.Service";

const noData = {
  type: { typeId: 1 },
  productName: "",
  description: "",
  price: "",
  sizes: [
    {
      sizeId: 1,
      count: 0,
    },
    {
      sizeId: 2,
      count: 0,
    },
    {
      sizeId: 3,
      count: 0,
    },
    {
      sizeId: 4,
      count: 0,
    },
    {
      sizeId: 5,
      count: 0,
    },
    {
      sizeId: 6,
      count: 0,
    },
    {
      sizeId: 7,
      count: 0,
    },
  ],
  image: "",
};

const defValues = {
  hasPic: false,
  currentValueType: "",
  currentValueSize: 1,
  currentAmount: 0,
};

const sizeMap = {
  XS: 0,
  S: 0,
  M: 0,
  L: 0,
  XL: 0,
  XXL: 0,
  zero: 0,
};

const optionsType = [
  {
    value: "1",
    label: "Мерч",
  },
  {
    value: "2",
    label: "Ит-артефакт",
  },
];

const optionsSize = [
  {
    value: 1,
    label: "Универсальный",
  },
  {
    value: 2,
    label: "XS, S, M, L, XL, XXL",
  },
];

const AddItemPage = () => {
  const [data, setItemData] = useState(noData);
  const [values, setValues] = useState(defValues);
  const [size, setSize] = useState(sizeMap);
  const filePicker = useRef(null);

  const addFile = () => {
    filePicker.current.click();
  };

  const handleChangeSize = () => {
    const sizeData = [];
    const sizesList = Object.values(size);
    sizesList.map((count, index) => {
      console.log(count, index);
      sizeData.push({
        sizeId: index + 1,
        count: count,
      });
    });
    setItemData((prev) => ({
      ...prev,
      sizes: sizeData,
    }));
  };

  const handleChange = (e) => {
    const image = e.target.files[0];
    const fileReader = new FileReader();
    fileReader.onloadend = () => {
      setItemData((prev) => ({
        ...prev,
        image: fileReader.result,
      }));
    };
    fileReader.readAsDataURL(image);
    setValues((prev) => ({
      ...prev,
      hasPic: true,
    }));
  };

  const handleUpload = async () => {
    if (!data.image) {
      alert("Пожалуйста добавьте картинку");
      return;
    }
    const fetchData = async () => {
      const response = await ItemService.addItem(data);
    };
    
    fetchData();
  };

  const getValueType = () => {
    return values.currentValueType
      ? optionsType.find((c) => c.value === values.currentValueType)
      : "";
  };

  const getValueSize = () => {
    return values.currentValueSize
      ? optionsSize.find((c) => c.value === values.currentValueSize)
      : "";
  };

  const onChangeType = (newValue) => {
    setValues((prev) => ({
      ...prev,
      currentValueType: newValue.value,
    }));
  };

  const onChangeSize = (newValue) => {
    setValues((prev) => ({
      ...prev,
      currentValueSize: newValue.value,
    }));
  };

  const onDropHandler = (e) => {
    e.preventDefault();
    const image = e.dataTransfer.files[0];
    const fileReader = new FileReader();
    fileReader.onloadend = () => {
      setItemData((prev) => ({
        ...prev,
        image: fileReader.result,
      }));
    };
    fileReader.readAsDataURL(image);
    setValues((prev) => ({
      ...prev,
      hasPic: true,
    }));
  };

  const dragStartHandler = (e) => {
    e.preventDefault();
  };

  const handleAddUniversalSize = (e) => {
    const pickedSizes = [0, 0, 0, 0, 0, 0, +e.target.value];
    setSize(pickedSizes);
    handleChangeSize()
  };

  // -----------------------------------------------------------------------------------------------------
  return (
    <>
      <div className={styles.content}>
        <div
          className={styles.itemImage}
          onClick={addFile}
          onDragStart={(e) => dragStartHandler(e)}
          onDragLeave={(e) => dragStartHandler(e)}
          onDragOver={(e) => dragStartHandler(e)}
          onDrop={(e) => onDropHandler(e)}
        >
          <div
            className={values.hasPic ? styles.fileImage : styles.invisible}
            style={
              values.hasPic ? { backgroundImage: `url(${data.image})` } : {}
            }
          ></div>
          <div
            className={!values.hasPic ? styles.addImgText : styles.invisible}
          >
            Загрузить фото
          </div>
          <div
            className={!values.hasPic ? styles.addImgIcon : styles.invisible}
          ></div>
          <input
            type="file"
            ref={filePicker}
            className={styles.invisible}
            required
            accept="image/*, .png, .jpg, .gif, .web, .jpeg, .svg"
            onChange={handleChange}
          />
        </div>
        <div className={styles.info}>
          <form className={styles.form} action="">
            <div>
              <p className={styles.text}>Тип</p>
              <Select
                options={optionsType}
                value={getValueType()}
                onChange={onChangeType}
                required
                onBlur={(e) =>
                  setItemData((prev) => ({
                    ...prev,
                    type: { typeId: values.currentValueType },
                  }))
                }
                placeholder="Выберите тип"
                className={styles.selector}
              />
            </div>
            <div>
              <p className={styles.text}>Название</p>
              <input
                onChange={(e) =>
                  setItemData((prev) => ({
                    ...prev,
                    productName: e.target.value,
                  }))
                }
                required
                value={data.name}
                className={styles.inputData}
                type="text"
              />
            </div>
            <div>
              <p className={styles.text}>Описание</p>
              <textarea
                onChange={(e) =>
                  setItemData((prev) => ({
                    ...prev,
                    description: e.target.value,
                  }))
                }
                value={data.description}
                className={styles.textData}
                name="description"
                id="description"
              ></textarea>
            </div>
            <div className={styles.sizeBlock}>
              <div>
                <p className={styles.text}>Цена</p>
                <input
                  onChange={(e) =>
                    setItemData((prev) => ({
                      ...prev,
                      price: e.target.value,
                    }))
                  }
                  required
                  value={data.price}
                  className={styles.price}
                  type="text"
                />
              </div>
              <div>
                <p className={styles.text}>Размер</p>
                <Select
                  options={optionsSize}
                  required
                  value={getValueSize()}
                  onChange={onChangeSize}
                  placeholder="Выберите размер"
                  className={styles.selector}
                />
              </div>
            </div>
            {values.currentValueSize == 1 ? (
              <div className={styles.countSize}>
                <p className={styles.textAmount}>Количество</p>
                <input
                  onChange={(e) => {
                    setValues((prev) => ({
                      ...prev,
                      currentAmount: e.target.value,
                    }));
                  }}
                  onBlur={(e) => handleAddUniversalSize(e)}
                  value={values.currentAmount}
                  className={styles.amount}
                  type="text"
                />
              </div>
            ) : (
              <div className={styles.sizesBigBlock}>
                <div className={styles.sizesBlock}>
                  <div className={styles.oneSizeBlock}>
                    <p className={styles.textAmount}>XS</p>
                    <input
                      onChange={(e) => {
                        setSize((prev) => ({
                          ...prev,
                          XS: +e.target.value,
                        }));
                      }}
                      onBlur={handleChangeSize}
                      value={size.XS}
                      className={styles.amount}
                      type="text"
                    />
                  </div>
                  <div className={styles.oneSizeBlock}>
                    <p className={styles.textAmount}>S</p>
                    <input
                      onChange={(e) => {
                        setSize((prev) => ({
                          ...prev,
                          S: +e.target.value,
                        }));
                      }}
                      onBlur={handleChangeSize}
                      value={size.S}
                      className={styles.amount}
                      type="text"
                    />
                  </div>
                  <div className={styles.oneSizeBlock}>
                    <p className={styles.textAmount}>M</p>
                    <input
                      onChange={(e) => {
                        setSize((prev) => ({
                          ...prev,
                          M: +e.target.value,
                        }));
                      }}
                      onBlur={handleChangeSize}
                      value={size.M}
                      className={styles.amount}
                      type="text"
                    />
                  </div>
                </div>
                <div className={styles.sizesBlock}>
                  <div className={styles.oneSizeBlock}>
                    <p className={styles.textAmount}>L</p>
                    <input
                      onChange={(e) => {
                        setSize((prev) => ({
                          ...prev,
                          L: +e.target.value,
                        }));
                      }}
                      onBlur={handleChangeSize}
                      value={size.L}
                      className={styles.amount}
                      type="text"
                    />
                  </div>
                  <div className={styles.oneSizeBlock}>
                    <p className={styles.textAmount}>XL</p>
                    <input
                      onChange={(e) => {
                        setSize((prev) => ({
                          ...prev,
                          XL: +e.target.value,
                        }));
                      }}
                      onBlur={handleChangeSize}
                      value={size.XL}
                      className={styles.amount}
                      type="text"
                    />
                  </div>
                  <div className={styles.oneSizeBlock}>
                    <p className={styles.textAmount}>XXL</p>
                    <input
                      onChange={(e) => {
                        setSize((prev) => ({
                          ...prev,
                          XXL: +e.target.value,
                        }));
                      }}
                      onBlur={handleChangeSize}
                      value={size.XXL}
                      className={styles.amount}
                      type="text"
                    />
                  </div>
                </div>
              </div>
            )}
            <button
              onClick={(e) => {
                e.preventDefault();
                handleUpload();
              }}
              className={styles.addItem}
            >
              Добавить товар
            </button>
          </form>
        </div>
      </div>
    </>
  );
};

export default AddItemPage;
