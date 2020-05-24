defmodule Silnia do
  def silnia(0)do 1 end
  def silnia(n) do silnia(n-1)*n end

end
