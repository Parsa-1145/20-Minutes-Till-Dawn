import pandas as pd
import matplotlib.pyplot as plt

# Read CSV (time and value columns)
df = pd.read_csv("./data.csv")

# Plot
plt.figure(figsize=(10, 5))
plt.plot(df["time"], df["value"], 'b-', label="Value vs Time")

# Customize plot
plt.title("Time vs Value Plot")
plt.xlabel("Time (s)")
plt.ylabel("Value")
plt.grid(True)
plt.legend()

plt.show()  # Display plot
# plt.savefig("plot.png")  # Save to file