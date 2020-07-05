import React, {Component} from 'react'
import {Table} from 'reactstrap';

class DataTable extends Component {

	render() {
		const items = this.props.items.map(item => {
			return (
				<tr key={item.id}>
					<th scope="row">{item.id}</th>
					<td>{item.userId}</td>
					<td>{item.currencyFrom}</td>
					<td>{item.currencyTo}</td>
					<td>{item.amountSell}</td>
					<td>{item.amountBuy}</td>
					<td>{item.rate}</td>
					<td>{item.timePlaced}</td>
					<td>{item.originatingCountry}</td>
				</tr>
			)
		})

		return (
			<Table responsive hover>
				<thead>
				<tr>
					<th>ID</th>
					<th>User Id</th>
					<th>Currency From</th>
					<th>Currency To</th>
					<th>Amount Sell</th>
					<th>Amount Buy</th>
					<th>Rate</th>
					<th>Time Placed</th>
					<th>Originating Country</th>
				</tr>
				</thead>
				<tbody>
				{items}
				</tbody>
			</Table>
		)
	}
}

export default DataTable