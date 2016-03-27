# SmartSprinkler
IOT Based smart sprinkler that works with Raspberry Pi. 

Hardware required:
1. Raspberry Pi 2
2. Moisture sensor
3. H-Bridge circuit
4. 5V water pump to demonstrate this idea


Note: The python code is missing which need to be included in this solns. 


Project Name: 
SmartSprinkler

Project Details: 
- Based on the data received from the moisture sensor, sprinkler will be activated. The sprinkler will be activated only when the moisture data is below a pre-determined level. This pre-determined level will be set for each crop type. This solution will help to water the right quantity based on the moisture level and the crop type.

- The weather forecast of that particular area will also be considered to determine the quantity of water. For example, if the chance of rain is 90% for that area, the quantity of water will be reduced to 10%.

- Machine Learning will also be enabled for the sprinkler. Based on the data from the previous watering cycles, the system will learn the optimum water to be sprinkled for the particular crop type. For example, if tomato plants were watered at 10am yesterday with 1 litre of water and the plants dried up (ie. moisture level is low) by 3pm. In today’s watering cycle, the system will water the plant with more water(example: 1.5 litres) so that the plants will be at optimum moisture till sun set.

- The farmer will be notified if the land has more moisture for a longer time. For example; if the moisture level is very high for a day incase of heavy rain, the farmer will be notified so that he can take necessary action(example: drain water and cover the crop area) to save the crop from rotting off.

- The data from sensor will be send to the cloud also. This data will be published to the farmers at the same demography who are not using SmartSprinkler and they can also water the plant optimally with the data.

Alternatives:
Manual and semi automatic sprinklers are available in the market. But this solution is completely automated and needs:
- No manual intervention and hence saves cost.
- Provides better crop health because of maintaining optimal moisture for the crop type
- Saves water 

